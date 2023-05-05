import schedule
import time

from miio.integrations.airpurifier.zhimi import airpurifier
import paho.mqtt.client as mqtt
import ssl

purifier = airpurifier.AirPurifier(
    ip="192.168.80.148", token="acf8b3ced11c488f447b5a662ff3cd95")

hono_mqtt_adaptor = "hono.eclipseprojects.io"
hono_mqtt_adaptor_port = 8883

device_id = "2a8f8cc9-1d89-4472-adb4-1a0ce4b47d57"
tenant_id = "d63d9625-4069-476a-827b-7e605754a3d0"

device_pwd = "this-is-my-password"

client = mqtt.Client(device_id)

client.username_pw_set(device_id+"@"+tenant_id, device_pwd)
client.tls_set("/etc/ssl/certs/ca-certificates.crt",
               tls_version=ssl.PROTOCOL_TLSv1_2)


def on_connect(client, userdata, flags, rc):
    print(rc)
    if rc != 0:
        print("Connection to MQTT broker failed: " + str(rc))
        return

    print(device_id+"@"+tenant_id)
    schedule.every(3).seconds.do(job)
    while True:
        schedule.run_pending()   # 运行所有可以运行的任务
        time.sleep(1)


def job():
    # "{\"aqi\":"+str(purifier.get_properties(["aqi"])[0])+"}"
    result = client.publish(
        "telemetry/"+tenant_id+"/"+device_id, "1",1)
    print(result.is_published())
    # print(purifier.get_properties(["aqi"])[0])


client.on_connect = on_connect

client.connect(host=hono_mqtt_adaptor, port=hono_mqtt_adaptor_port,
               keepalive=60, bind_address="")

client.loop_start()

while (1):
    pass

# def main():
#     print(device_id+"@"+tenant_id)
#     schedule.every(3).seconds.do(job)
#     while True:
#         schedule.run_pending()   # 运行所有可以运行的任务
#         time.sleep(1)


# if __name__ == '__main__':
#     main()
