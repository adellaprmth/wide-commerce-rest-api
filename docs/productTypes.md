# Product Types

## Get All Product Types

Endpoint : GET /api/product_types

Response Body :
```json
{
  "data": [
    {
      "id": "uuid",
      "name": "product_types_name",
      "code": "product_types_code" //random string
    }
  ]
}
```

## Get Product Types By ID

Endpoint : GET /api/product_types/{idProductTypes}

Response Body (success) :
```json
{
  "data": {
      "id": "uuid",
      "name": "product_types_name",
      "code": "product_types_code"
    }
}
```

Response Body (failed) :
```json
{
  "message": "Product Types Not Found"
}
```

## Add Product Types

Endpoint : POST /api/product_types

Request Body :
```json
{
  "name": "product_type_name"
}
```

Response Body (success) :
```json
{
  "data": {
    "id": "uuid",
    "name": "product_types_name",
    "code": "product_types_code"
  }
}
```

Response Body (failed) :
```json
{
  "message": "Product Type Already Exist."
}
```

## Update Product Types

Endpoint : PATCH /api/product_types/{idProductTypes}

Request Body :
```json
{
  "name": "product_types_name"
}
```

Response Body (success) :
```json
{
  "data": {
    "id": "uuid",
    "name": "product_types_name",
    "code": "product_types_code"
  }
}
```

Response Body (failed) :
```json
{
  "message": "Product Type Not Found"
}
```

## Remove Product Types

Endpoint : DELETE /api/product_types/{idProductTypes}

Response Body (success) :
```json
{
  "data": "Removed"
}
```

Response Body (failed) :
```json
{
  "message": "Product Type Not Found"
}
```