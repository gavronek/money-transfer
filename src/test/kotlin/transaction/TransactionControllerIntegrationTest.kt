package transaction

import ApplicationRunner
import account.Account
import account.AccountTestClient
import io.restassured.module.kotlin.extensions.Then
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal


@ExtendWith(ApplicationRunner::class)
class TransactionControllerIntegrationTest {
    private val accountClient = AccountTestClient(8000)
    private val transactionClient = TransactionTestClient(8000)

    private lateinit var account1 : Account
    private lateinit var account2 : Account

    @BeforeEach
    internal fun setUp() {
        account1 = accountClient.createAccount(1000f, "EUR")
        account2 = accountClient.createAccount(2000f, "EUR")
    }

    @Test
    internal fun `test posting transaction`() {
        val transaction = transactionClient.createTransaction(account1.id, account2.id, 100f, "EUR")

        assertThat(accountClient.get(account1.id).balance, equalTo(BigDecimal("900.0")))
        assertThat(accountClient.get(account2.id).balance, equalTo(BigDecimal("2100.0")))
    }

    @Test
    internal fun `test failure scenarios for transaction creation`() {
        transactionClient.createRequest(account1.id, account2.id, -1f, "EUR")
            .Then { statusCode(400) }
        transactionClient.createRequest(account1.id, account2.id, 100f, "invalid-currency")
            .Then { statusCode(400) }
        transactionClient.createRequest("not-existent", account2.id, 100f, "EUR")
            .Then { statusCode(404) }
        transactionClient.createRequest(account1.id, account2.id, 9999f, "EUR")
            .Then { statusCode(400) }
        transactionClient.createRequest(account1.id, "not-existent", 100f, "EUR")
            .Then { statusCode(404) }
    }

    @Test
    internal fun `test get transaction methods`() {
        val transaction = transactionClient.createTransaction(account1.id, account2.id, 100f, "EUR")

        assertThat(accountClient.get(account1.id).balance, equalTo(BigDecimal("900.0")))
        assertThat(accountClient.get(account2.id).balance, equalTo(BigDecimal("2100.0")))
        assertThat(transactionClient.get(transaction.id), equalTo(transaction))
        assertThat(transactionClient.getAll(), equalTo(listOf(transaction)))
    }

    @Test
    internal fun `test failure scenarios for get transaction`() {
        transactionClient.getRequest("not-found").Then { statusCode(404) }
    }


    @Test
    internal fun `test delete is not allowed`() {
        transactionClient.deleteRequest("some-id").Then { statusCode(405) }
    }

    @Test
    internal fun `test update is not allowed`() {
        transactionClient.patchRequest("some-id").Then { statusCode(405) }
    }
}