# User API Specs
## Registrasi User
Endpoint : POST/api/users

Request Body : 
```json
{
  "username" : "afisfisabillah",
  "password" : "rahasia",
  "name" : "Afis Fisabilillah"
}
```
Response Body (succes)
```json
{
  "data" : "ok"
}
```
Response Body (failed)
```json
{
  "data" : "ko",
  "errors" : "Username must not blank"
}
```

## Login User
Endpoint : POST/api/auth/login

Request Body :
```json
{
  "username" : "afisfisabillah",
  "password" : "rahasia"
}
```
Response Body (succes)
```json
{
  "data" : {
    "token" : "32132adoinvcUNnhDQDWQ",
    "expiredAt" : 13213213
  }
}
```
Response Body (failed, 401)
```json
{
  "errors" : "Username or Password Wrong"
}
```

## Get User
Endpoint : GET /api/user/

Response Body (succes)

Request Header :
- X-API-TOKEN

```json
{
  "data" : {
    "nama" : "Afis Fisabilillah",
    "username" : "afisfisabilillah"
  }
}
```
Response Body (failed, 401)
```json
{
  "errors" : "unauthorize"
}
```

[//]: # (update)
## Update User
Endpoint : PATCH /api/user/update

Request Body
```json
{
  "username" : "dsadsa",//put if you want
  "password" : "rahasia"
}
```

Response Body (succes)
```json
{
  "data" : {
    "nama" : "Afis Fisabilillah",
    "username" : "afisfisabilillah"
  }
}
```
Response Body (failed, 401)
```json
{
  "errors" : "unauthorize"
}
```