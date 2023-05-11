import schedule
import time
import paho.mqtt.client as mqtt
import ssl
import json
from Pole import Pole

hono_mqtt_adaptor_host = "hono.eclipseprojects.io"
hono_mqtt_adaptor_port = 8883

device_id = "8eeee125-eaf7-4ccc-a14d-c7fea4f94c2b"
tenant_id = "04c8405b-bb66-4b03-b823-e3117108b9b2"
device_pwd = "your-pwd"        # 运行前须修改

pub_interval = 3  # seconds

pole = Pole()


def pub_telemetry():
    client.loop()
    if client.is_connected():
        result = client.publish("telemetry", json.dumps(
            pole.get_status(), default=vars))
        result.wait_for_publish()


def on_connect(client, userdata, flags, rc):
    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe("$SYS/#")
    client.subscribe("command///req/#")


def on_message(client, userdata, msg):
    cmd = msg.topic.split("command///req//", 1)[1]
    if cmd == "setStatus":
        param = json.loads(msg.payload)
        status = param.get('status')
        id = param.get('id')
        if status == 'UP':
            if id is not None:
                if id in range(0, pole.pole_num):
                    pole.up_one(id)
            else:
                pole.up_all()
        elif param['status'] == 'DOWN':
            if id is not None:
                if id in range(0, pole.pole_num):
                    pole.down_one(id)
            else:
                pole.down_all()


if __name__ == '__main__':
    client = mqtt.Client(device_id)
    client.username_pw_set(device_id+"@"+tenant_id, device_pwd)
    client.tls_set('./ca-certificates.crt',
                   tls_version=ssl.PROTOCOL_TLSv1_2)
    client.connect(host=hono_mqtt_adaptor_host, port=hono_mqtt_adaptor_port,
                   keepalive=60, bind_address="")
    client.on_connect = on_connect
    client.on_message = on_message

    schedule.every(pub_interval).seconds.do(pub_telemetry)

    while True:
        time.sleep(1)
        schedule.run_pending()