from fastapi import FastAPI, Request, Depends, status, HTTPException
from fastapi.responses import HTMLResponse, JSONResponse
from fastapi.templating import Jinja2Templates
from fastapi.exceptions import RequestValidationError
from fastapi.encoders import jsonable_encoder
from datetime import date
from starlette.staticfiles import StaticFiles
from schemas import CustomForm, ExRate, FxRate, get_cat_link, process_currency, combined
import httpx
import asyncio


app = FastAPI()
templates = Jinja2Templates(directory="templates")
app.mount("/static", StaticFiles(directory="static"), name="static")


@app.exception_handler(RequestValidationError)
async def validation_exception_handler(request: Request, exc: RequestValidationError):
    errors = []
    to_remove = ['url', 'input']
    for error in exc.errors():
        for rm in to_remove:
            if rm in error:
                del error[rm]
        errors.append(error)
    return JSONResponse(
        status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
        content=jsonable_encoder({"Code": 422, "Errors": errors}),
    )


@app.get('/', response_class=HTMLResponse, status_code=201)
async def get_basic_form(request: Request):
    context = {"myOptions": combined}
    return templates.TemplateResponse(request=request, name="index.html", context=context)


@app.post('/results', response_class=HTMLResponse, status_code=202)
async def post_form(request: Request, form: CustomForm = Depends(CustomForm.as_form)):
    context = await parse_request(form)
    return templates.TemplateResponse(request=request, name="results.html", context=context)


async def fetch_data(form: CustomForm) -> tuple:
    urls = [
        get_cat_link(),
        ExRate.get_singleton().get_link(form),
        FxRate.get_singleton().get_link(form),
    ]
    async with httpx.AsyncClient() as client:
        reqs = [client.get(url) for url in urls]
        result = await asyncio.gather(*reqs)

    return result


async def parse_request(form: CustomForm) -> dict:
    if not form.start_date <= form.end_date <= date.today():
        if form.start_date > form.end_date:
            msg = "Start date must be before end date"
        else:
            msg = "End date must be before today's date"
        raise HTTPException(
            status_code=404,
            detail=msg,
        )

    result = await fetch_data(form)
    for data in result:
        if data.status_code >= 400:
            raise HTTPException(
                status_code=500,
                detail="Resources not found",
            )

    cat, ex_rate, fx_rate = result
    process_currency(fx_rate, form)

    context = {
        "cat": cat.json()[0]['url'],
        "conversion_rate": ex_rate.json()['conversion_rate'],
        "curr1": form.currency1,
        "curr2": form.currency2
    }

    return context

