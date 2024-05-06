from google.protobuf.internal import containers as _containers
from google.protobuf.internal import enum_type_wrapper as _enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class MatchInfo(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
    __slots__ = ()
    MATCH_INFO_START: _ClassVar[MatchInfo]
    MATCH_INFO_GOAL: _ClassVar[MatchInfo]
    MATCH_INFO_MID_MATCH_STATS: _ClassVar[MatchInfo]
    MATCH_INFO_FIRST_HALF_STATS: _ClassVar[MatchInfo]
    MATCH_INFO_WHOLE_MATCH_STATS: _ClassVar[MatchInfo]
MATCH_INFO_START: MatchInfo
MATCH_INFO_GOAL: MatchInfo
MATCH_INFO_MID_MATCH_STATS: MatchInfo
MATCH_INFO_FIRST_HALF_STATS: MatchInfo
MATCH_INFO_WHOLE_MATCH_STATS: MatchInfo

class Nothing(_message.Message):
    __slots__ = ()
    def __init__(self) -> None: ...

class Subscription(_message.Message):
    __slots__ = ("team", "subscribe")
    TEAM_FIELD_NUMBER: _ClassVar[int]
    SUBSCRIBE_FIELD_NUMBER: _ClassVar[int]
    team: str
    subscribe: bool
    def __init__(self, team: _Optional[str] = ..., subscribe: bool = ...) -> None: ...

class SubscriptionReply(_message.Message):
    __slots__ = ("msg",)
    MSG_FIELD_NUMBER: _ClassVar[int]
    msg: _containers.RepeatedScalarFieldContainer[str]
    def __init__(self, msg: _Optional[_Iterable[str]] = ...) -> None: ...

class Team(_message.Message):
    __slots__ = ("name", "short")
    NAME_FIELD_NUMBER: _ClassVar[int]
    SHORT_FIELD_NUMBER: _ClassVar[int]
    name: str
    short: str
    def __init__(self, name: _Optional[str] = ..., short: _Optional[str] = ...) -> None: ...

class Stats(_message.Message):
    __slots__ = ("info", "names", "score", "minute", "shots", "possession", "passes")
    INFO_FIELD_NUMBER: _ClassVar[int]
    NAMES_FIELD_NUMBER: _ClassVar[int]
    SCORE_FIELD_NUMBER: _ClassVar[int]
    MINUTE_FIELD_NUMBER: _ClassVar[int]
    SHOTS_FIELD_NUMBER: _ClassVar[int]
    POSSESSION_FIELD_NUMBER: _ClassVar[int]
    PASSES_FIELD_NUMBER: _ClassVar[int]
    info: MatchInfo
    names: Teams
    score: Score
    minute: int
    shots: Shots
    possession: Possession
    passes: Passes
    def __init__(self, info: _Optional[_Union[MatchInfo, str]] = ..., names: _Optional[_Union[Teams, _Mapping]] = ..., score: _Optional[_Union[Score, _Mapping]] = ..., minute: _Optional[int] = ..., shots: _Optional[_Union[Shots, _Mapping]] = ..., possession: _Optional[_Union[Possession, _Mapping]] = ..., passes: _Optional[_Union[Passes, _Mapping]] = ...) -> None: ...

class Score(_message.Message):
    __slots__ = ("team1", "team2")
    TEAM1_FIELD_NUMBER: _ClassVar[int]
    TEAM2_FIELD_NUMBER: _ClassVar[int]
    team1: int
    team2: int
    def __init__(self, team1: _Optional[int] = ..., team2: _Optional[int] = ...) -> None: ...

class Teams(_message.Message):
    __slots__ = ("team1", "team2")
    TEAM1_FIELD_NUMBER: _ClassVar[int]
    TEAM2_FIELD_NUMBER: _ClassVar[int]
    team1: Team
    team2: Team
    def __init__(self, team1: _Optional[_Union[Team, _Mapping]] = ..., team2: _Optional[_Union[Team, _Mapping]] = ...) -> None: ...

class Shots(_message.Message):
    __slots__ = ("team1", "team2")
    TEAM1_FIELD_NUMBER: _ClassVar[int]
    TEAM2_FIELD_NUMBER: _ClassVar[int]
    team1: int
    team2: int
    def __init__(self, team1: _Optional[int] = ..., team2: _Optional[int] = ...) -> None: ...

class Possession(_message.Message):
    __slots__ = ("team1", "team2")
    TEAM1_FIELD_NUMBER: _ClassVar[int]
    TEAM2_FIELD_NUMBER: _ClassVar[int]
    team1: float
    team2: float
    def __init__(self, team1: _Optional[float] = ..., team2: _Optional[float] = ...) -> None: ...

class Passes(_message.Message):
    __slots__ = ("team1", "team2")
    TEAM1_FIELD_NUMBER: _ClassVar[int]
    TEAM2_FIELD_NUMBER: _ClassVar[int]
    team1: int
    team2: int
    def __init__(self, team1: _Optional[int] = ..., team2: _Optional[int] = ...) -> None: ...
