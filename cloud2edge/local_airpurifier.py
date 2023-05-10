import argparse
from functools import partial
import json
from typing import Any
import paho.mqtt.client as mqtt
from miio.integrations.airpurifier.zhimi import airpurifier

COMMAND_PREFIX: str = "/inbox/messages"
POWER: str = "/power"


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tenant-id", type=str, required=True)
    parser.add_argument("--device-id", type=str, required=True)
    parser.add_argument("--device-pwd", type=str, required=True)
    parser.add_argument("--auth-id", type=str, required=False)
    parser.add_argument("--host", type=str, required=True)
    parser.add_argument("--port", type=int, default=1883)

    args = parser.parse_args()
    if args.auth_id is None:
        args.auth_id = args.device_id
    return args


def init_device() -> airpurifier.AirPurifier:
    return airpurifier.AirPurifier(
        ip="192.168.80.148",
        token="acf8b3ced11c488f447b5a662ff3cd95",
    )


def on_connect(client: mqtt.Client, _, flags: dict[str, Any], rc: int):
    if rc != 0:
        print("Failed to connect to MQTT broker")
        return
    print(f"Create connection to MQTT broker, flags: {flags}. Subscribing...")
    client.subscribe("$SYS/#")
    client.subscribe("command///req/#")


def on_message(
    device: airpurifier.AirPurifier,
    client: mqtt.Client,
    _,
    msg: mqtt.MQTTMessage,
):
    payload = json.loads(msg.payload)
    path: str = payload["path"]
    value: dict[str, Any] = payload["value"]
    if not path.startswith(COMMAND_PREFIX):
        print(f"Ignoring command event: {path}")
        return
    path = path.removeprefix(COMMAND_PREFIX)
    if path == POWER:
        power: str = value["power"].lower()
        if power == "off":
            device.off()
            print("Air Purifier is OFF")
        elif power == "on":
            device.on()
            print("Air Purifier is ON")
        else:
            print(f"Unknown power value: {value['power']}")
    else:
        print(f"Unknown command: {path}")


def main():
    args = parse_args()
    device = init_device()

    adapter = mqtt.Client()
    adapter.username_pw_set(f"{args.auth_id}@{args.tenant_id}", args.device_pwd)
    adapter.connect(args.host, args.port)

    adapter.on_connect = on_connect
    adapter.on_message = partial(on_message, device)
    try:
        adapter.loop_forever()
    except KeyboardInterrupt:
        adapter.disconnect()


if __name__ == "__main__":
    main()
