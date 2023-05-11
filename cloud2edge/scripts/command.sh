curl -i -X POST -u ditto:ditto -H 'Content-Type: application/json' -w '\n' --data '{
  "power": "off"
}' http://192.168.49.2:31024/api/2/things/org.i2ec:air-purifier/inbox/messages/power?timeout=0
