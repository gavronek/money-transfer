package account

import ApplicationRunner
import io.restassured.module.kotlin.extensions.Then
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ApplicationRunner::class)
class AccountControllerIntegrationTest {
    private val accountClient = AccountTestClient(8000)

    @Test
    internal fun `test account creation`() {
        accountClient.createAccount(1001.25f, "EUR")
    }

    @Test
    internal fun `test failure scenarios for account creation`() {
        accountClient.createRequest(-0.1f, "EUR").Then { statusCode(400) }
        accountClient.createRequest(1f, "not-valid-currency").Then { statusCode(400) }
    }

    @Test
    internal fun `test get account methods`() {
        val account1 = accountClient.createAccount(505.11f, "EUR")
        val account2 = accountClient.createAccount(303.33f, "GBP")

        assertThat(accountClient.getAll(), Matchers.containsInAnyOrder(account1, account2))
        assertThat(accountClient.get(account1.id), Matchers.equalTo(account1))
    }

    @Test
    internal fun `test failure scenarios for get account`() {
        accountClient.getRequest("not-found").Then { statusCode(404) }
    }


    @Test
    internal fun `test delete account`() {
        accountClient.deleteRequest("some-id").Then { statusCode(405) }
    }

    @Test
    internal fun `test update is not allowed`() {
        accountClient.patchRequest("some-id").Then { statusCode(405) }
    }
}