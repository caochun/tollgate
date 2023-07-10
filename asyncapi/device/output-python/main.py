#!/usr/bin/env python3
import configparser
import logging
import time

import messaging

from poleSetStatus import PoleSetStatus
from poleGetStatus import PoleGetStatus
from sentAt import SentAt

from poles import Poles

station_poles = Poles(9)

# Config has the connection properties.
def getConfig():
    configParser = configparser.ConfigParser()
    configParser.read('config.ini')
    config = configParser['DEFAULT']
    return config


def poleSetStatus(client, userdata, msg):
    jsonString = msg.payload.decode('utf-8')
    logging.info('Received json: ' + jsonString)
    poleSetStatus = PoleSetStatus.from_json(jsonString)
    logging.info('Received message: ' + str(poleSetStatus))
    station_poles.set_pole_status(poleSetStatus.id, poleSetStatus.status)



def main():
    logging.basicConfig(level=logging.INFO)
    logging.info('Start of main.')
    config = getConfig()

    poleSetStatusMessenger = messaging.Messaging(config, 'pole.setStatus', poleSetStatus)
    poleSetStatusMessenger.loop_start()
    poleGetStatusMessenger = messaging.Messaging(config)
    
    while (True):
        payload = PoleGetStatus(station_poles.to_poles_list(), SentAt.get_timestamp())
        payloadJson = payload.to_json()
        poleGetStatusMessenger.publish('pole.getStatus', payloadJson)
        time.sleep(1)

if __name__ == '__main__':
    main()

