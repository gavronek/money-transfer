# Get specific account

Get the current data for a specific account

**URL** : `/accounts/:accountId`

**Method** : `GET`

## Success Response

**Condition** : If everything is OK and an account exists with the id

**Code** : `200`

**Content example**

```json
{
    "balance": 1000.0,
    "currency": "EUR",
    "id": "ebbd05e8-1f15-4270-a5b6-65cc7640867b"
}
```

## Error Responses

**Condition** : If account with the given id is not found

**Code** : `404 NOT FOUND`
