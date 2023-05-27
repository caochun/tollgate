import schedule
import time
import paho.mqtt.client as mqtt
import ssl
import json
from Pole import Pole

ditto_mqtt_adaptor_host = "ditto_host_ip"    # 运行前须修改
ditto_mqtt_adaptor_port = 31883

thing_id = "org.i2ec:poles"
device_id = "org.i2ec:poles"
auth_id = "poles"
tenant_id = "org.i2ec"
device_pwd = "your-pwd"        # 运行前须修改

pub_interval = 20  # seconds

pole = Pole()


def pub_telemetry():
    payload = pole.get_status()
    command = {
        "topic": thing_id.replace(":", "/") + "/things/twin/commands/modify",
        "value": payload,
        "path": "/features/polestatus/properties",
        "headers": {},
    }
    client.publish("telemetry", json.dumps(command, default=vars))


def on_connect(client, userdata, flags, rc):
    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe("$SYS/#")
    client.subscribe("command///req/#")


def on_message(client, userdata, msg):
    payload = json.loads(msg.payload)
    print(payload)
    path = payload["path"]
    if path.startswith("/inbox/messages"):
        cmd = path.removeprefix("/inbox/messages")
        if cmd == "/setstatus":
            param = payload["value"]
            status = param.get('status')
            id = param.get('id')
            if status == 'UP':
                if id is not None:
                    if id in range(0, pole.pole_num):
                        pole.up_one(id)
                else:
                    pole.up_all()
            elif status == 'DOWN':
                if id is not None:
                    if id in range(0, pole.pole_num):
                        pole.down_one(id)
                else:
                    pole.down_all()
            pub_telemetry()


if __name__ == '__main__':
    client = mqtt.Client()
    client.username_pw_set(auth_id+"@"+tenant_id, device_pwd)
    client.on_connect = on_connect
    client.on_message = on_message
    client.connect(host=ditto_mqtt_adaptor_host, port=ditto_mqtt_adaptor_port,
                   keepalive=60, bind_address="")

    schedule.every(pub_interval).seconds.do(pub_telemetry)

    while True:
        client.loop()
        time.sleep(1)
        schedule.run_pending()