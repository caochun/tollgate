import schedule
import time

from miio.integrations.airpurifier.zhimi import airpurifier
import paho.mqtt.client as mqtt
import ssl
import json

purifier = airpurifier.AirPurifier(
    ip="192.168.80.148", token="acf8b3ced11c488f447b5a662ff3cd95")

hono_mqtt_adaptor_host = "hono.eclipseprojects.io"
hono_mqtt_adaptor_port = 8883

device_id = "2a8f8cc9-1d89-4472-adb4-1a0ce4b47d57"
tenant_id = "d63d9625-4069-476a-827b-7e605754a3d0"

device_pwd = "this-is-my-password"

pub_interval = 3 #seconds

client = mqtt.Client(device_id)

client.username_pw_set(device_id+"@"+tenant_id, device_pwd)
client.tls_set("/etc/ssl/certs/ca-certificates.crt",
               tls_version=ssl.PROTOCOL_TLSv1_2)

def pub_telemetry():
    client.loop()
    if client.is_connected():
        print(purifier.status())
        result = client.publish("telemetry", json.dumps(purifier.status().data,default=vars))
        result.wait_for_publish()

def on_connect(client, userdata, flags, rc):
    
    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe("$SYS/#")



def on_message(client, userdata, msg):
    print(msg.topic+" "+str(msg.payload))



client.on_connect = on_connect
client.on_message = on_message

client.connect(host=hono_mqtt_adaptor_host, port=hono_mqtt_adaptor_port,
               keepalive=60, bind_address="")

schedule.every(pub_interval).seconds.do(pub_telemetry)

while True:
    time.sleep(1)
    schedule.run_pending() 

