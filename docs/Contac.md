# Contac Api Spec

## Create Contac

Endpoint : POST api/contacs  

Request Body : 

```json
{
  "firstname" : "afis",
  "lastname"  : "fisabilillah",
  "email" : "afis@gmail.com",
  "phone" : "094324324324"
}
``` 

Response Body (succes) :

```json
{
  "data" : {
    "id" : "random",
    "firstname" : "afis",
    "lastname"  : "fisabilillah",
    "email" : "afis@gmail.com",
    "phone" : "094324324324"
  }
}
```

Response Body (Failde) :

```json
{
  "message" : "error,",
  "errors" : {
    "name" : "dsadsads"
  }
}
```

## Update Contac

Endpoint : PUT /api/contacs/{id}

Request Body :
```json
{
  "firstname" : "afis",
  "lastname"  : "fisabilillah",
  "email" : "afis@gmail.com",
  "phone" : "094324324324"
}
```

Response Body (succes) :
```json
{
  "data" : {
    "id" : "random",
    "firstname" : "afis",
    "lastname"  : "fisabilillah",
    "email" : "afis@gmail.com",
    "phone" : "094324324324"
  }
}
```


Response Body (Failde) :

```json
{
  "message" : "error,",
  "errors" : {
    "name" : "dsadsads"
  }
}
```

## Get Contac

Endpoint : GET api/contac/{id}

Request Body :

Response Body (succes) :

```json
{
  "data" : {
    "id" : "random",
    "firstname" : "afis",
    "lastname"  : "fisabilillah",
    "email" : "afis@gmail.com",
    "phone" : "094324324324"
  }
}
```


Response Body (Failde, 404) :

```json
{
  "errors" : "Contacs Not Found"
}
```

## Search Contac

Endpoint : GET /api/contacs

Query Param :

- name : Strig name,
- font

Request Body :

Response Body (succes) :

Response Body (Failde) :

## Remove COntac

Endpoint : DELETE /api/contac/{idContac}

Request Body :

Response Body (succes) :
```json
{
  "message" : "data berhasil di hapus"
}
```

Response Body (Failde) :
