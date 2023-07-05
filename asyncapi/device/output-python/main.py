#!/usr/bin/env python3
import configparser
import logging
import time

import messaging

from payload import SetStatusPayload, GetStatusPayload
from poles import Poles

station_poles = Poles(9)

# Config has the connection properties.
def getConfig():
    configParser = configparser.ConfigParser()
    configParser.read('config.ini')
    config = configParser['DEFAULT']
    return config


def poleSetstatus(client, userdata, msg):
    jsonString = msg.payload.decode('utf-8')
    logging.info('Received json: ' + jsonString)
    payload = SetStatusPayload.from_json(jsonString)
    logging.info('Received message: ' + str(payload))
    station_poles.set_pole_status(payload.id, payload.status)


def get_timestamp():
    now = int(time.time())
    time_array = time.localtime(now)
    time_style = time.strftime('%Y-%m-%d %H:%M:%S', time_array)
    return time_style


def main():
    logging.basicConfig(level=logging.INFO)
    logging.info('Start of main.')
    config = getConfig()

    poleSetstatusMessenger = messaging.Messaging(config, 'pole/setstatus', poleSetstatus)
    poleSetstatusMessenger.loop_start()
    poleGetstatusMessenger = messaging.Messaging(config)

    while (True):
        payload = GetStatusPayload(station_poles.to_poles_list(), get_timestamp())
        payloadJson = payload.to_json()
        poleGetstatusMessenger.publish('pole/getstatus', payloadJson)
        time.sleep(1)

if __name__ == '__main__':
    main()

