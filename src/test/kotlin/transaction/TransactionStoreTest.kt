package transaction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.Currency.getInstance

internal class TransactionStoreTest {
    private val database = mutableMapOf<String, Transaction>()

    private val store = TransactionStore(database)

    private val transactionId1 = "tr1"
    private val transaction1 = Transaction(
        transactionId1,
        "a1",
        "a2",
        BigDecimal.ONE,
        getInstance("USD")
    )

    private val transactionId2 = "tr2"
    private val transaction2 = transaction1.copy(id = transactionId2)

    @Test
    internal fun `test creating transaction`() {
        store.create(transaction1)

        assertEquals(database[transactionId1], transaction1)
    }

    @Test
    internal fun `test get all transaction returns empty list`() {
        assertEquals(store.getAll(), listOf<Transaction>())
    }

    @Test
    internal fun `test get all transaction`() {
        database[transactionId1] = transaction1
        database[transactionId2] = transaction2

        assertEquals(store.getAll(), listOf(transaction1, transaction2))
    }

    @Test
    internal fun `test get one by id not found`() {
        assertEquals(store.getOne("not-in-there"), null)
    }

    @Test
    internal fun `test get one by id`() {
        database[transactionId1] = transaction1

        assertEquals(store.getOne(transactionId1), transaction1)
    }
}