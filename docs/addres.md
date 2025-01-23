# ADDRESS API SPEC

## Create Addres

Endpoint : Post /api/contac/{id contac}/addres

Request Body : 

```json
{
  "street" : "jalan",
  "city" : "kota",
  "provience" : "Provinsi",
  "country" : "Country  Negara",
  "postalCode" : "dsadsa"
}
```

Response Body (succes) :

```json
{
  "data" :{ 
    "id" :  "Rndowmaitring ",
    "street" : "jalan",
    "city" : "kota",
    "provience" : "Provinsi",
    "country" : "Country  Negara",
    "postalCode" : "dsadsa"
  }
}
```

Response Body (Failed) :

```json
{
  "message" : "maaf data gagal di tambahkan", 
  "errors" : {
    "dsadsadsa" : "dsadsa"
  } 
}
```

## Update Addres

Endpoint : PUT 

Request Body :

Response Body (succes) :

Response Body (Failed) :

## GET Addres 

Endpoint :

Request Body :

Response Body (succes) :

Response Body (Failed) :

## Remove Addres 

Endpoint :

Request Body :

Response Body (succes) :

Response Body (Failed) :

