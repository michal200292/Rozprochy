from fastapi import FastAPI
from dataclasses import dataclass, field

app = FastAPI()


@dataclass
class Vote:
    user_name: str
    poll_name: str
    option: str


@dataclass
class Poll:
    name: str
    question: str
    options: list[str] = field(default_factory=lambda: [])
    user_votes: dict[str, Vote] = field(default_factory=lambda: {})

    def __post_init__(self):
        self.votes: list[int] = [0 for _ in range(len(self.options))]


polls: dict[str, Poll] = {
    "Pizza": Poll(
        "Pizza", 
        "Choose your favourite pizza style", 
        ["Detroit", "Neapolitan", "New York"]
    ), 
    "Sport": Poll(
        "Sport", 
        "What is your favourite sport activity", 
        ["Running", "Cycling", "Volleyball, Football"]
    )  
}

# sample requests and queries
@app.get("/")
async def root() :
    return {"message": "Hello World"}


@app.get("/poll/{poll_name}")
async def get_poll(poll_name: str):
    if poll_name not in polls:
        return "No such poll"
    return polls[poll_name]


@app.get("/poll/{poll_name}/vote")
async def get_polls_votes(poll_name: str):
    if poll_name not in polls:
        return {"message": "No such poll"}
    poll = polls[poll_name]
    
    votes_count = sum([c for c in poll.votes])
    if not votes_count:
        return {"message": "No votes were casted in this poll"}
    
    count = [f"{(100*c / votes_count):.2f} %" for c in poll.votes]
    return dict(zip(poll.options, count))


@app.post("/poll")
async def create_poll(name: str, question: str, options: list[str]):
    new_poll = Poll(name, question, options)
    polls[name] = new_poll
    return new_poll


@app.post("/poll/{poll_name}/vote/{user_name}")
async def cast_vote(user_name: str, poll_name: str, option: str):
    if poll_name not in polls:
        return {"message": "No such poll"}
    
    poll = polls[poll_name]
    if option not in poll.options:
        return {"message": "No such option"}
    
    index = poll.options.index(option)
    poll.votes[index] += 1
    new_vote = Vote(user_name, poll_name, option)
    poll.user_votes[user_name] = new_vote
    return new_vote


@app.put("/poll/{poll_name}/vote/{user_name}")
async def change_vote(user_name: str, poll_name: str, option: str):
    if poll_name not in polls:
        return {"message": "No such poll"}
    
    poll = polls[poll_name]
    
    if user_name not in poll.user_votes:
        new_vote = await cast_vote(user_name, poll_name, option)
        return new_vote
    else:  
        if option not in poll.options:
            return {"message": "No such option"}

        last_option = poll.user_votes[user_name].option
        
        index_last = poll.options.index(last_option)
        index_new = poll.options.index(option)
        poll.votes[index_last] -= 1
        poll.votes[index_new] += 1
        
        new_vote = Vote(user_name, poll_name, option)
        poll.user_votes[user_name] = new_vote
        return new_vote

@app.delete("/poll/{poll_name}/vote/delete")
async def delete_vote(user_name: str, poll_name: str):
    if poll_name not in polls:
        return {"message": "No such poll"}
    
    poll = polls[poll_name]
    if user_name not in poll.user_votes:
        return {"message": "No votes were casted by this user"}
    
    option = poll.user_votes[user_name].option
    index = poll.options.index(option)
    poll.votes[index] -= 1
    poll.user_votes.pop(user_name)
    return None
