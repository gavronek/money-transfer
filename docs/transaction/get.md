# Get specific transaction

Get the current data for a specific account

**URL** : `/transactions/:transactionId`

**Method** : `GET`

## Success Response

**Condition** : If everything is OK and an account exists with the id

**Code** : `200`

**Content example**

```json
{
    "amount": 100.0,
    "currency": "EUR",
    "destination": "0bdeaf7d-f3a3-47de-a44a-16fb721a93e3",
    "id": "374bf288-46c2-45d4-aa83-6d1c255886c9",
    "source": "c2642ae1-2b24-4551-96e5-65f07e8114fe"
}
```

## Error Responses

**Condition** : If transaction with the given id is not found

**Code** : `404 NOT FOUND`
