# Products

## Get All Product

Endpoint : GET /api/products

Query Parameter :
- page : Integer, start from 1, default 1
- size : Integer, default 15

Response Body :
```json
{
  "data": [
    {
      "id": "uuid",
      "name": "product_name",
      "code": "product_code", //random string
      "type": "produc_type",
      "price": 100000,
      "stock": 100
    }
  ],
  "meta": {
    "current_page": 1,
    "total_page": 10,
    "size": 15,
    "total_data": 150
  }
}
```

## Get Product By ID

Endpoint : GET /api/products/{idProduct}

Response Body (success) :
```json
{
  "data": {
      "id": "uuid",
      "name": "product_name",
      "code": "product_code",
      "type": "product_type",
      "price": 100000,
      "stock": 100
    }
}
```

Response Body (failed) :
```json
{
  "message": "Product Not Found"
}
```

## Add Product

Endpoint : POST /api/products

Request Body :
```json
{
  "name": "product_name",
  "type": "product_type",
  "price": 10000,
  "stock": 10
}
```

Response Body (success):
```json
{
  "data": {
    "id": "uuid",
    "name": "product_name",
    "code": "product_code",
    "type": "product_type",
    "price": 10000,
    "stock": 10
  }
}
```

Response Body (failed):
```json
{
  "message": "Product Already Exist."
}
```

## Update Product

Endpoint : PATCH /api/products/{idProduct}

Request Body :
```json
{
  "name": "product_name", //optional
  "product_type": "product_type", //optional
  "price": 100000, //optional
  "stock": 10 //optional
}
```

Response Body (success) :
```json
{
  "name": "product_name",
  "product_type": "product_type",
  "price": 100000,
  "stock": 10
}
```

Response Body (failed) :
```json
{
  "message": "Product Not Found."
}
```

## Remove Product

Endpoint : DELETE /api/products/{idProduct}

Response Body (success) :
```json
{
  "data" : "Removed"
}
```

Response Body (failed) :
```json
{
  "message" : "Product Not Found"
}
```