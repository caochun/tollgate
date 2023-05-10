curl -i -X PUT -H "Content-Type: application/json" --data '[
{
  "type": "hashed-password",
  "auth-id": "air-purifier",
  "secrets": [{
    "pwd-plain": "password"
  }]
}]' http://192.168.49.2:31080/v1/credentials/i2ec-devices/org.i2ec.devices:air-purifier