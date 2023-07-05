from enum import Enum
from typing import Sequence, List
from entity import Entity

from pole import Pole

class GetStatusPayload(Entity):

    def __init__(
            self,
            poles: List[Pole],
            sentAt: str):
        self.poles = poles
        self.sentAt = sentAt


class SetStatusPayload(Entity):

    def __init__(
            self,
            id: int,
            status: int,
            sentAt: str):
        self.id = id
        self.status = status
        self.sentAt = sentAt


