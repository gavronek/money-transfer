# Money-service

All endpoints accepting requests without authentication. 

## Account management

Each endpoint manipulates or displays information related to an account

* [List accounts](account/list.md) : `GET /accounts`
* [Create account](account/post.md) : `POST /accounts`
* [View an account](account/get.md) : `GET /accounts/:accountId`

## Transaction management

Endpoints to create and show transactions between accounts

* [List transactions](transaction/list.md) : `GET /transactions`
* [Create transaction](transaction/post.md) : `POST /transactions`
* [View a transaction](transaction/get.md) : `GET /transactions/:transactionId`
