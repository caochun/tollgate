import schedule
import time

from miio.integrations.airpurifier.zhimi import airpurifier

purifier = airpurifier.AirPurifier(
        ip="192.168.80.148", token="acf8b3ced11c488f447b5a662ff3cd95")

def job():
    print(purifier.get_properties(["aqi"]))


def main():
    schedule.every(1).second.do(job)
    while True:
        schedule.run_pending()   # 运行所有可以运行的任务
        time.sleep(1)
    
if __name__ == '__main__':
    main()
