package account

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import io.restassured.response.ResponseBodyExtractionOptions
import org.hamcrest.Matchers

class AccountTestClient(private val port: Int) {
    fun createAccount(balance: Float, currency: String) : Account {
        val body =  createRequest(balance, currency) Then {
            statusCode(201)
            body("id", Matchers.not(Matchers.emptyOrNullString()))
                .and().body("balance", Matchers.equalTo(balance))
                .and().body("currency", Matchers.equalTo(currency))
        } Extract { body() }
        return body.asEntity()
    }

    fun createRequest(balance: Float, currency: String): Response {
        return Given {
            port(port)
            body("""{ "balance":$balance, "currency":"$currency" }""")
        } When {
            post("/accounts")
        }
    }

    fun getAll() : List<Account> {
        val body = getAllRequest() Then {
            statusCode(200)
        } Extract {
            body()
        }
        return body.asEntity<Array<Account>>().toList()
    }

    fun getAllRequest(): Response {
        return Given {
            port(port)
        } When {
            get("/accounts")
        }
    }

    fun get(id: String) : Account {
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
            pathParam("accountId", id)
        } When {
            get("/accounts/{accountId}")
        }
    }

    fun delete(id: String) {
        deleteRequest(id) Then {
            statusCode(200)
        }
    }

    fun deleteRequest(id: String): Response {
        return Given {
            port(port)
            pathParam("accountId", id)
        } When {
            delete("/accounts/{accountId}")
        }
    }

    fun patchRequest(id: String): Response {
        return Given {
            port(port)
            pathParam("accountId", id)
        } When {
            patch("/accounts/{accountId}")
        }
    }

    private inline fun <reified T: Any> ResponseBodyExtractionOptions.asEntity(): T = this.`as`(T::class.java)
}