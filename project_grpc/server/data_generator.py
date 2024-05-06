import random
import time
from stub.python.match_pb2 import MatchInfo, Score, Stats, Possession, Shots, Passes, Teams, Team
from typing import Iterator

name_to_short = {
    "Arsenal FC": "ARS",
    "Aston Villa": "AVL",
    "Brentford": "BRE",
    "Bournemouth": "BOU",
    "Brighton & Hove Albion": "BRI",
    "Burnley FC": "BUR",
    "Chelsea FC": "CHE",
    "Crystal Palace": "CRY",
    "Fulham": "FUL",
    "Everton FC": "EVE",
    "Luton Town FC": "LUT",
    "Liverpool FC": "LIV",
    "Manchester City": "MCI",
    "Manchester United": "MUN",
    "Newcastle United": "NEW",
    "Sheffield United": "SHU",
    "Tottenham Hotspur": "TOT",
    "West Ham United": "WHU",
    "Wolverhampton Wanderers": "WOL",
    "Nottingham Forest": "NFO"
}

short_to_name = {v: k for k, v in name_to_short.items()}
short_names = sorted(list(name_to_short.values()))


def generate_info(minute: int, goal: bool) -> MatchInfo:
    if goal:
        return MatchInfo.MATCH_INFO_GOAL
    if minute == 90:
        return MatchInfo.MATCH_INFO_WHOLE_MATCH_STATS
    elif minute == 45:
        return MatchInfo.MATCH_INFO_FIRST_HALF_STATS
    else:
        return MatchInfo.MATCH_INFO_MID_MATCH_STATS


def update_score(last_score: Score, goal: bool) -> Score:
    if not goal:
        return last_score
    t1 = random.randrange(2)
    t2 = 1 - t1
    new_score = Score(
        team1=last_score.team1 + t1,
        team2=last_score.team2 + t2,
    )
    return new_score


def update_shots(last_shots: Shots, goal: bool, time_passed: int) -> Shots:
    return Shots(
        team1=last_shots.team1 + random.randrange(1 if goal else 0, time_passed // 5 + 2),
        team2=last_shots.team2 + random.randrange(1 if goal else 0, time_passed // 5 + 2)
    )


def update_possession(last_poss: Possession, minute: int, time_passed: int) -> Possession:
    time_percentage = time_passed / minute
    p1_new = random.uniform(0, 1)
    p1 = last_poss.team1*(1 - time_percentage) + p1_new*time_percentage

    return Possession(
        team1=p1,
        team2=1 - p1
    )


def update_passes(last_passes: Passes, time_passed: int) -> Passes:
    return Passes(
        team1=last_passes.team1 + random.randrange(time_passed * 10),
        team2=last_passes.team2 + random.randrange(time_passed * 10)
    )


def generate_new_stats(last_stats: Stats, minute: int, goal: bool) -> Stats:
    time_passed = minute - last_stats.minute
    return Stats(
        info=generate_info(minute, goal),
        names=last_stats.names,
        score=update_score(last_stats.score, goal),
        minute=minute,
        shots=update_shots(last_stats.shots, goal, time_passed),
        possession=update_possession(last_stats.possession, minute, time_passed),
        passes=update_passes(last_stats.passes, time_passed)
    )


def generate_match_data(short1: str, short2: str) -> Iterator[Stats]:
    last_stats = Stats(
        info=MatchInfo.MATCH_INFO_START,
        names=Teams(
            team1=Team(
                short=short1,
                name=short_to_name[short1]
            ),
            team2=Team(
                short=short2,
                name=short_to_name[short2]
            )
        ),
        score=Score(
            team1=0,
            team2=0
        ),
        minute=0,
        shots=Shots(
            team1=0,
            team2=0
        ),
        possession=Possession(
            team1=1.0,
            team2=0.0
        ),
        passes=Passes(
            team1=0,
            team2=0
        )
    )

    yield last_stats

    for minute in range(1, 91):
        time.sleep(0.2)
        if random.uniform(0, 1) >= 0.95:
            new_stats = generate_new_stats(last_stats, minute, True)
            yield new_stats
            last_stats = new_stats

        elif minute % 15 == 0:
            new_stats = generate_new_stats(last_stats, minute, False)
            yield new_stats
            last_stats = new_stats
    return


def main() -> None:
    gen = generate_match_data("ARS", "CHE")
    while True:
        try:
            stats = next(gen)
            print(stats)
        except StopIteration:
            break


if __name__ == "__main__":
    main()

