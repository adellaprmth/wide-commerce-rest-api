# Order Cart

## Get User Cart
Endpoint: GET /api/cart
Request Header:
- Authorization: Bearer $TOKEN

Response Body:

```json
{
  "data": [
    {
      "productId": "",
      "quantity": 1,
      "price": 10000
    }
  ],
  "totalPrice": 10000
}
```

## Add Product to Cart
Endpoint: POST /api/cart


Request Body
```json
[
  {
    "productId": "",
    "quantity": 1
  }
]
```

## Delete Product from Cart
Endpoint DELETE /api/cart/product/{productId}


## Update quantity
Endpoint PATCH /api/cart/product/{productId}

Request Body:

```json
{
  "quantity": 10
}
```

## Checkout
Endpoint GET /api/cart/checkout
