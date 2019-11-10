package account

import io.javalin.http.BadRequestResponse
import io.javalin.http.NotFoundResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import transaction.Transaction
import java.math.BigDecimal
import java.util.Currency.getInstance as currency

internal class AccountStoreTest {
    private val database = mutableMapOf<String, Account>()
    private val store = AccountStore(database)

    private val accountId1 = "testId1"
    private val account1 = Account(accountId1, BigDecimal.TEN, currency("USD"))

    private val accountId2 = "testId2"
    private val account2 = Account(accountId1, BigDecimal.TEN, currency("USD"))

    private val succesfulTransaction = Transaction(
        "testTransaction",
        accountId1,
        accountId2,
        BigDecimal.ONE,
        currency("USD")
    )

    @Test
    internal fun `test creation`() {
        assertEquals(store.create(account1), account1)
        assertEquals(database[accountId1], account1)
    }

    @Test
    internal fun `test get all`() {
        database[accountId1] = account1
        assertEquals(store.getAll(), listOf(account1))
    }

    @Test
    internal fun `test get all returns empty list if no account`() {
        assertEquals(store.getAll(), emptyList<Account>())
    }

    @Test
    internal fun `test get`() {
        database[accountId1] = account1
        assertEquals(store.getOne(accountId1), account1)
    }

    @Test
    internal fun `test get does not find id`() {
        assertNull(store.getOne("not-in-db"))
    }

    @Test
    internal fun `executing transaction fails if source account is not found`() {
        assertThrows<NotFoundResponse> { store.execute(succesfulTransaction) }
    }

    @Test
    internal fun `executing transaction fails if source is not in right currency`() {
        database[accountId1] = account1.copy(currency = currency("GBP"))
        assertThrows<BadRequestResponse> { store.execute(succesfulTransaction) }
    }

    @Test
    internal fun `executing transaction fails if source does not have sufficient balance`() {
        database[accountId1] = account1
        assertThrows<BadRequestResponse> { store.execute(succesfulTransaction.copy(amount = BigDecimal.valueOf(1000L))) }
    }

    @Test
    internal fun `executing transaction fails if destination not found`() {
        database[accountId1] = account1
        assertThrows<NotFoundResponse> { store.execute(succesfulTransaction) }
    }

    @Test
    internal fun `executing transaction fails if destination is not in right currency`() {
        database[accountId1] = account1
        database[accountId2] = account2.copy(currency = currency("GBP"))

        assertThrows<BadRequestResponse> { store.execute(succesfulTransaction) }
    }

    @Test
    internal fun `successfully applied transaction`() {
        database[accountId1] = account1
        database[accountId2] = account2

        store.execute(succesfulTransaction)

        assertEquals(database.getValue(accountId1).balance, BigDecimal.valueOf(9))
        assertEquals(database.getValue(accountId2).balance, BigDecimal.valueOf(11))
    }
}