package transaction

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import io.restassured.response.ResponseBodyExtractionOptions
import org.hamcrest.Matchers.*

class TransactionTestClient(private val port: Int) {
    fun createTransaction(sourceAccountId: String, destinationAccountId: String, amount: Float, currency: String) : Transaction {
        val body =  createRequest(sourceAccountId, destinationAccountId, amount, currency) Then {
            statusCode(201)
            body("id", not(emptyOrNullString()))
                .and().body("source", equalTo(sourceAccountId))
                .and().body("destination", equalTo(destinationAccountId))
                .and().body("amount", equalTo(amount))
                .and().body("currency", equalTo(currency))
        } Extract { body() }
        return body.asEntity()
    }

    fun createRequest(sourceAccountId: String, destinationAccountId: String, amount: Float, currency: String): Response {
        return Given {
            port(port)
            body("""{ 
                |"source": "$sourceAccountId", 
                |"destination": "$destinationAccountId", 
                |"amount":$amount, 
                |"currency":"$currency" 
                |}""".trimMargin())
        } When {
            post("/transactions")
        }
    }

    fun getAll() : List<Transaction> {
        val body = getAllRequest() Then {
            statusCode(200)
        } Extract {
            body()
        }
        return body.asEntity<Array<Transaction>>().toList()
    }

    fun getAllRequest(): Response {
        return Given {
            port(port)
        } When {
            get("/transactions")
        }
    }

    fun get(id: String) : Transaction {
        val body = getRequest(id) Then {
            statusCode(200)
        } Extract  {
            body()
        }

        return body.asEntity()
    }

    fun getRequest(id: String): Response {
        return Given {
            port(port)
            pathParam("transactionId", id)
        } When {
            get("/transactions/{transactionId}")
        }
    }

    fun deleteRequest(id: String): Response {
        return Given {
            port(port)
            pathParam("transactionId", id)
        } When {
            delete("/transactions/{transactionId}")
        }
    }

    fun patchRequest(id: String): Response {
        return Given {
            port(port)
            pathParam("transactionId", id)
        } When {
            patch("/transactions/{transactionId}")
        }
    }

    private inline fun <reified T: Any> ResponseBodyExtractionOptions.asEntity(): T = this.`as`(T::class.java)
}