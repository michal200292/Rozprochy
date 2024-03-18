from fastapi import Form
from pydantic import BaseModel
from datetime import date
from abc import ABC, abstractmethod
import os
from dotenv import load_dotenv
import matplotlib.pyplot as plt
import numpy as np


load_dotenv()


class CustomForm(BaseModel):
    currency1: str
    currency2: str
    start_date: date
    end_date: date

    @classmethod
    def as_form(
            cls,
            currency1: str = Form(...),
            currency2: str = Form(...),
            start_date: date = Form(...),
            end_date: date = Form(...),
    ):
        return cls(
            currency1=currency1,
            currency2=currency2,
            start_date=start_date,
            end_date=end_date,
        )


class Exchange(ABC):
    _singleton: 'Exchange' = None

    def __init__(self, key):
        self.api_key: str = key

    @classmethod
    def get_singleton(cls):
        if cls._singleton is None:
            cls._singleton = cls(cls.get_api_key())
        return cls._singleton

    @classmethod
    @abstractmethod
    def get_api_key(cls):
        ...

    @abstractmethod
    def get_link(self, form: CustomForm):
        ...


class ExRate(Exchange):
    _singleton: 'ExRate' = None

    @classmethod
    def get_api_key(cls):
        api_key: str = os.getenv("EX_RATE_API_KEY")
        return api_key

    def get_link(self, form: CustomForm):
        link = f"https://v6.exchangerate-api.com/v6/" \
               f"{self.api_key}/pair/{form.currency1}/{form.currency2}"
        return link


class FxRate(Exchange):
    _singleton: 'FxRate' = None

    @classmethod
    def get_api_key(cls):
        api_key: str = os.getenv("FX_RATE_API_KEY")
        return api_key

    def get_link(self, form: CustomForm):
        link = f"https://api.fxratesapi.com/" \
               f"timeseries?start_date={form.start_date}&end_date={form.end_date}"
        return link


def get_cat_link():
    return "https://api.thecatapi.com/v1/images/search"


def process_data(data, form: CustomForm):
    values1 = []
    values2 = []
    for rate in data.json()['rates']:
        for currency, val in data.json()['rates'][rate].items():
            if currency.lower() == form.currency1.lower():
                values1.append(val)
            if currency.lower() == form.currency2.lower():
                values2.append(val)

    values1, values2 = np.array(values1), np.array(values2)

    plt.show()

    plt.plot(list(range(len(values1))), values2 / values1)
    plt.xlabel(f"{form.currency1}")
    plt.ylabel(f"{form.currency2}")
    plt.title(f"Comparison of {form.currency1} and {form.currency2} "
              f"from {form.start_date} to {form.end_date}")

    plt.savefig('static/currency.png')

