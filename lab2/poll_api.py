from fastapi import FastAPI
from dataclasses import dataclass, field

app=FastAPI( )


@dataclass
class Vote:
    vote_id: int
    option: str


@dataclass
class Poll:
    id: int
    name: str
    question: str
    options: list[str] = field(default_factory=lambda: [])
    votes: dict[int, Vote] = field(default_factory=lambda: {})

    def __post_init__(self):
        self.vote_count: list[int] = [0]*len(self.options)


@app.get("/")
async def root() :
    return {"message" : "Hello World"}
