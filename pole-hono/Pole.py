from ctypes import *
import time

poleDLL = CDLL("./MockPole.dll")


class PoleStruct(Structure):
    _fields_ = [("pole_status", c_int*100),    # 所有抬杆状态
                ("pole_num", c_int)]           # 抬杆数量


class PoleJson:
    def __init__(self, poles_list):
        self.timestamp = self.__get_timestamp()
        self.poles = self.__pack_status_json(poles_list)

    def __get_timestamp(self):
        now = int(time.time())
        time_array = time.localtime(now)
        time_style = time.strftime('%Y-%m-%d %H:%M:%S', time_array)
        return time_style

    def __pack_status_json(self, poles_list):
        poles_json_list = []
        for i, one_status in enumerate(poles_list):
            poles_json_list.append({'id': i, 'status': one_status})
        return poles_json_list


class Pole:
    def __init__(self):
        self.status, self.pole_num = self.__get_pole_status()
        print(self.status)

    def __get_pole_status(self):
        poleDLL.IO_GetPoleStatus.restype = POINTER(PoleStruct)    # 设置从dll中获取抬杆状态函数的返回结果类型
        tempdata = poleDLL.IO_GetPoleStatus()
        pole_num = tempdata.contents.pole_num
        poles_list = []
        for i in range(pole_num):
            poles_list.append(tempdata.contents.pole_status[i])
        return poles_list, pole_num

    def up_all(self):
        poleDLL.IO_UpAllPoles()

    def up_one(self, id):
        poleDLL.IO_UpOnePole(id)

    def down_all(self):
        poleDLL.IO_DownAllPoles()

    def down_one(self, id):
        poleDLL.IO_DownOnePole(id)

    def get_status(self):
        self.status, _ = self.__get_pole_status()
        return PoleJson(self.status)
