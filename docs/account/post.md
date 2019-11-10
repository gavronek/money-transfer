# Create an account

Create an account with a starting balance and currency.

**URL** : `/accounts/`

**Method** : `POST`

**Data constraints**

* Starting balance must be positive or 0
* Provided currency has to be a valid ISO 4217 currency code

**Data example** All fields must be sent.

```json
{
  "balance": 1000,
  "currency": "EUR"
}
```

## Success Response

**Condition** : If everything is OK and all validation passed.

**Code** : `201 CREATED`

**Content example**

```json
{
    "balance": 1000.0,
    "currency": "EUR",
    "id": "ebbd05e8-1f15-4270-a5b6-65cc7640867b"
}
```

## Error Responses

**Condition** : If fields are missing or data constraints were violated.

**Code** : `400 BAD REQUEST`

**Content example**

```json
{
    "details": [],
    "status": 400,
    "title": "Couldn't deserialize body to AccountCreateCommand",
    "type": "https://javalin.io/documentation#badrequestresponse"
}
```
