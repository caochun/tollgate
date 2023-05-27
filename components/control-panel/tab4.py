import asyncio
import os
import typing
import schedule
import time
import ssl
import sys
import json
from threading import Thread

from PyQt5 import QtCore
from PyQt5.QtWidgets import QWidget, QPushButton, QHBoxLayout, QVBoxLayout, \
    QGridLayout, QTabWidget, QLineEdit, QFileDialog, QLabel, QProgressBar, QMessageBox
from PyQt5.QtGui import QIcon
from PyQt5.QtCore import pyqtSlot, QObject, pyqtSignal

import paho.mqtt.client as mqtt

# from Pole import Pole

class poleSignals(QObject):
    poleStatusUpdate = pyqtSignal(list)

class tab4(QWidget):
    def __init__(self, parent):
        super(QWidget, self).__init__(parent)
        self.layout = QVBoxLayout()

        self.poleSignal = poleSignals()
        self.poleSignal.poleStatusUpdate.connect(self.dataUpdate)

        self.poleStatus = [{'id': i, 'status': '0'} for i in range(9)]
        self.upAll = QPushButton('全部抬起')
        self.upAll.clicked.connect(lambda: self.on_click_all('UP'))
        self.downAll = QPushButton('全部落下')
        self.downAll.clicked.connect(lambda: self.on_click_all('DOWN'))
        
        self.hWidget = QWidget()
        self.poleStatusLayout = QHBoxLayout()
        for pole in self.poleStatus:
            vWidget = QWidget()
            vLayout = QVBoxLayout()
            button = QPushButton('切换')
            button.clicked.connect(lambda: self.on_click(pole["id"]))
            vLayout.addWidget(QLabel(f'{pole["id"]}号杆: {"抬起" if pole["status"] == "1" else "落下"}'))
            vLayout.addWidget(button)
            vWidget.setLayout(vLayout)
            vWidget.setObjectName(f'pole{pole["id"]}')
            self.poleStatusLayout.addWidget(vWidget)
        self.hWidget.setLayout(self.poleStatusLayout)

        self.layout.addWidget(self.hWidget)
        self.layout.addWidget(self.upAll)
        self.layout.addWidget(self.downAll)

        self.setLayout(self.layout)

        # Initialize Hono Connection
        self.hono_mqtt_adaptor_host = "hono.eclipseprojects.io"
        self.hono_mqtt_adaptor_port = 8883

        self.device_id = "8eeee125-eaf7-4ccc-a14d-c7fea4f94c2b"
        self.tenant_id = "04c8405b-bb66-4b03-b823-e3117108b9b2"
        self.device_pwd = "your-pwd"

        self.pub_interval = 3 # seconds
        
        thread = Thread(target=self.start)
        thread.daemon = True
        thread.start()

    @pyqtSlot(list)
    def dataUpdate(self, value):
        for pole in value:
            self.findChild(QWidget).findChild(QWidget, f'pole{pole["id"]}').findChild(QLabel).setText(
                f'{pole["id"]}号杆: {"抬起" if pole["status"] == "1" else "落下"}'
            )

    def on_click(self, pole_num):
        self.client.loop()
        if self.client.is_connected():
            result = self.client.publish(f"command/{self.tenant_id}/{self.device_id}/req//setStatus", json.dumps(
                { 'id': pole_num, 'status': 'DOWN' if self.poleStatus[pole_num]['status'] == '1' else 'UP' }
            ))
            result.wait_for_publish()
    
    def on_click_all(self, status):
        self.client.loop()
        print(status)
        if self.client.is_connected():
            result = self.client.publish(f"command/{self.tenant_id}/{self.device_id}/req//setStatus", json.dumps(
                { 'status': status }
            ))
            result.wait_for_publish()
            print("Result sent.")
        else:
            print("Client Disconnected!")

    def on_connect(self, client, userdata, flags, rc):
        # Subscribing in on_connect() means that if we lose the connection and
        # reconnect then subscriptions will be renewed.
        client.subscribe("$SYS/#")
        client.subscribe("telemetry/" + self.tenant_id)

    def on_message(self, client, userdata, msg):
        param = json.loads(msg.payload)
        print(param)
        self.poleSignal.poleStatusUpdate.emit(param)

    def start(self):
        self.client = mqtt.Client()
        self.client.username_pw_set(self.device_id+"@"+self.tenant_id, self.device_pwd)
        self.client.tls_set('./control-panel/ca-certificates.crt',
                    tls_version=ssl.PROTOCOL_TLSv1_2)
        self.client.connect(host=self.hono_mqtt_adaptor_host, port=self.hono_mqtt_adaptor_port,
                    keepalive=60, bind_address="")
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
