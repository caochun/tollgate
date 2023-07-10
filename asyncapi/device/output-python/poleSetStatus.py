from enum import Enum
from typing import Sequence
from entity import Entity



class PoleSetStatus(Entity):

    def __init__(
            self,
            id: int,
            status: int,
            sentAt: str):
        self.id = id
        self.status = status
        self.sentAt = sentAt


