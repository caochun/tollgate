from enum import Enum
from typing import Sequence
from entity import Entity
import time


class SentAt(Entity):

    def __init__(
            self):
        pass

    @classmethod
    def get_timestamp(cls):
        now = int(time.time())
        time_array = time.localtime(now)
        time_style = time.strftime('%Y-%m-%d %H:%M:%S', time_array)
        return time_style


