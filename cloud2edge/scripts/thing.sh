curl -i -X PUT -u ditto:ditto -H 'Content-Type: application/json' --data '{
  "features": {
    "status": {
      "properties": {
        "power": {
            "value": "off"
        },
        "aqi": {
            "value": 0
        },
        "led": {
            "value": "on"
        }
      } 
    }
  }
}' http://192.168.49.2:31024/api/2/things/org.i2ec:air-purifier
