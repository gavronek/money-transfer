# List transactions

List currently stored transactions

**URL** : `/transactions/`

**Method** : `GET`

## Success Response

**Condition** : If everything is OK

**Code** : `200`

**Content example**

```json
[
    {
        "amount": 100.0,
        "currency": "EUR",
        "destination": "0bdeaf7d-f3a3-47de-a44a-16fb721a93e3",
        "id": "0f508c54-b982-4546-92f9-b63710e71af8",
        "source": "c2642ae1-2b24-4551-96e5-65f07e8114fe"
    },
    {
        "amount": 100.0,
        "currency": "EUR",
        "destination": "0bdeaf7d-f3a3-47de-a44a-16fb721a93e3",
        "id": "342c9a52-64a1-4430-a7da-75c21c4085ae",
        "source": "c2642ae1-2b24-4551-96e5-65f07e8114fe"
    },
    {
        "amount": 100.0,
        "currency": "EUR",
        "destination": "0bdeaf7d-f3a3-47de-a44a-16fb721a93e3",
        "id": "374bf288-46c2-45d4-aa83-6d1c255886c9",
        "source": "c2642ae1-2b24-4551-96e5-65f07e8114fe"
    }
]
```
