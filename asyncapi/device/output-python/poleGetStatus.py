from enum import Enum
from typing import Sequence
from entity import Entity

class Pole(Entity):

    def __init__(
            self,
            id: int,
            status: int):
        self.id = id
        self.status = status

class PoleGetStatus(Entity):

    def __init__(
            self,
            poles: Sequence[Pole],
            sentAt: str):
        self.poles = poles
        self.sentAt = sentAt


