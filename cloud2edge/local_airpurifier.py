import argparse
import asyncio
from functools import partial
import json
from typing import Any
import paho.mqtt.client as mqtt
from miio.integrations.airpurifier.zhimi import airpurifier
import schedule

COMMAND_PREFIX: str = "/inbox/messages"
POWER: str = "/power"
LED: str = "/led"


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tenant-id", type=str, required=True)
    parser.add_argument("--device-id", type=str, required=True)
    parser.add_argument("--device-pwd", type=str, required=True)
    parser.add_argument("--auth-id", type=str, required=False)
    parser.add_argument("--thing-id", type=str, required=False)
    parser.add_argument("--host", type=str, required=True)
    parser.add_argument("--port", type=int, default=1883)

    args = parser.parse_args()
    if args.auth_id is None:
        args.auth_id = args.device_id
    if args.thing_id is None:
        args.thing_id = f"{args.tenant_id}:{args.device_id}"
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


def on_command(
    device: airpurifier.AirPurifier,
    client: mqtt.Client,
    _,
    msg: mqtt.MQTTMessage,
):
    payload = json.loads(msg.payload)
    path: str = payload["path"]
    if not path.startswith(COMMAND_PREFIX) or "value" not in payload:
        print(f"Ignoring command event: {path}")
        return
    value: dict[str, Any] = payload["value"]
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
    elif path == LED:
        led: str = value["led"].lower()
        if led == "off":
            device.set_led(False)
            print("LED is OFF")
        elif led == "on":
            device.set_led(True)
            print("LED is ON")
        else:
            print(f"Unknown LED value: {value['led']}")
    else:
        print(f"Unknown command: {path}")


def publish_state(thing_id: str, client: mqtt.Client, device: airpurifier.AirPurifier):
    client.loop()
    power, led, aqi = device.get_properties(["power", "led", "aqi"])
    payload = {
        "power": {"value": power},
        "aqi": {"value": aqi},
        "led": {"value": led},
    }
    command = {
        "topic": thing_id.replace(":", "/") + "/things/twin/commands/modify",
        "value": payload,
        "path": "/features/status/properties",
        "headers": {},
    }
    client.publish("telemetry", json.dumps(command))
    print(f"Published state: {command}")


def main():
    args = parse_args()
    device = init_device()
    adapter = mqtt.Client()
    adapter.username_pw_set(f"{args.auth_id}@{args.tenant_id}", args.device_pwd)
    adapter.connect(args.host, args.port)

    adapter.on_connect = on_connect
    adapter.on_message = partial(on_command, device)
    schedule.every(5).seconds.do(lambda: publish_state(args.thing_id, adapter, device))

    async def _run():
        while True:
            adapter.loop()
            schedule.run_pending()
            await asyncio.sleep(1)

    asyncio.run(_run())


if __name__ == "__main__":
    main()
