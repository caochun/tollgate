from poleGetStatus import Pole

class Poles:
    def __init__(self, pole_num):
        self.poles = [0 for _ in range(pole_num)]

    def set_pole_status(self, id, status):
        self.poles[id] = status

    def to_poles_list(self):
        l = []
        for id, status in enumerate(self.poles):
            l.append(Pole(id, status))
        return l