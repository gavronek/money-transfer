package transaction

import account.AccountStore
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.MethodNotAllowedResponse
import io.javalin.http.NotFoundResponse
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal.TEN
import java.util.Currency.getInstance

internal class TransactionControllerTest {
    private val accountStore = mockk<AccountStore>()
    private val transactionStore = mockk<TransactionStore>()
    private val context = mockk<Context>()


    private val controller = TransactionController(transactionStore, accountStore)

    private val transaction = Transaction("t1",  "acc1", "acc2", TEN, getInstance("USD"))
    private val transaction2 = transaction.copy(id = "t2")



    @Test
    internal fun `test successful transaction created`() {
        val newTransaction = TransactionCreateCommand("acc1", "acc2", TEN, getInstance("USD"))
        every { context.body<TransactionCreateCommand>() } returns newTransaction

        every { accountStore.execute(any()) } returns Unit
        every { transactionStore.create(any()) } returnsArgument(0)

        every { context.status(201) } returns context
        every { context.json(match<Transaction> {
            it.source == "acc1"
                    && it.destination == "acc2"
                    && it.amount == TEN
                    && it.currency == getInstance("USD")
        })} returns context

        controller.create(context)
    }

    @Test
    internal fun `should fail if request deserialization fails`() {
        every { context.body<TransactionCreateCommand>() } throws BadRequestResponse()

        assertThrows<BadRequestResponse> { controller.create(context) }

        verify(exactly = 0) { accountStore.execute(any()) }
        verify(exactly = 0) { transactionStore.create(any()) }
        verify(exactly = 0) { context.json(any()) }
    }

    @Test
    internal fun `test get all`() {
        every { transactionStore.getAll() } returns listOf(transaction, transaction2)
        every { context.json(listOf(transaction, transaction2)) } returns context
        controller.getAll(context)
    }

    @Test
    internal fun `test get`() {
        every { transactionStore.getOne("t1") } returns transaction
        every { context.json(transaction) } returns context

        controller.getOne(context, "t1")
    }

    @Test
    internal fun `should fail if get not found`() {
        every { transactionStore.getOne("not-seen") } returns null
        assertThrows<NotFoundResponse> { controller.getOne(context, "not-seen") }
        verify { context wasNot Called }
    }

    @Test
    internal fun `test update not implemented`() {
        assertThrows<MethodNotAllowedResponse> { controller.update(context, "testId") }
    }

    @Test
    internal fun `test delete not implemented`() {
        assertThrows<MethodNotAllowedResponse> { controller.delete(context, "testId") }
    }

}