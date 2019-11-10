# Create an account

File a money transfer transaction between two accounts.

**URL** : `/transactions/`

**Method** : `POST`

**Data constraints**

* Referenced source account must exist and have sufficient balance 
* Referenced destination account must exist
* Amount must be positive
* Both source and destination account must have the same currency as the transaction

**Data example** All fields must be sent.

```json
{
  "source": "c2642ae1-2b24-4551-96e5-65f07e8114fe",
  "destination": "0bdeaf7d-f3a3-47de-a44a-16fb721a93e3",
  "amount": 100,
  "currency": "EUR"
}
```

## Success Response

**Condition** : If everything is OK and all validation passed.

**Code** : `201 CREATED`

**Content example**

```json
{
    "amount": 100.0,
    "currency": "EUR",
    "destination": "0bdeaf7d-f3a3-47de-a44a-16fb721a93e3",
    "id": "24166094-da9d-4495-89e7-9be47a2d3453",
    "source": "c2642ae1-2b24-4551-96e5-65f07e8114fe"
}
```

## Error Responses

**Condition** : 
* Fields are missing or
* Source account's balance is not enough to execute
* Currency of transaction does not match across accounts 

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

### OR

**Condition** : If either source or destination account does not exist

**Code** : `404 NOT FOUND`

**Content example**

```json
{
    "details": [],
    "status": 404,
    "title": "Destination account does not exist",
    "type": "https://javalin.io/documentation#notfoundresponse"
}
```
