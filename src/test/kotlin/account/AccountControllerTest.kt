package account

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
import java.math.BigDecimal
import java.util.*


internal class AccountControllerTest {
    private val store = mockk<AccountStore>()
    private val controller = AccountController(store)
    private val context = mockk<Context>()

    private val account1 = Account("testId1", BigDecimal.ONE, Currency.getInstance("USD"))
    private val account2 = Account("testId2", BigDecimal.ONE, Currency.getInstance("USD"))

    @Test
    internal fun `test creation`() {
        val balance = BigDecimal("12.25")
        val currency = Currency.getInstance("USD")

        val accountCreateCommand = AccountCreateCommand(balance, currency)

        every { context.body<AccountCreateCommand>() } returns accountCreateCommand
        every { store.create(any()) } returnsArgument (0)

        every { context.status(201) } returns context
        every { context.json(match<Account> { it.balance == balance && it.currency == currency }) } returns context

        controller.create(context)        every { store.delete("testId") } returns null

    }

    @Test
    internal fun `should fail if request deserialization fails`() {
        every { context.body<AccountCreateCommand>() } throws BadRequestResponse()

        assertThrows<BadRequestResponse> { controller.create(context) }

        verify(exactly = 0) { store.create(any()) }
        verify(exactly = 0) { context.json(any()) }
    }

    @Test
    internal fun `test get all`() {
        every { store.getAll() } returns listOf(account1, account2)
        every { context.json(listOf(account1, account2)) } returns context
        controller.getAll(context)
    }

    @Test
    internal fun `test get`() {
        every { store.getOne("testId") } returns account1
        every { context.json(account1) } returns context

        controller.getOne(context, "testId")
    }

    @Test
    internal fun `should fail if get not found`() {
        every { store.getOne("testId") } returns null
        assertThrows<NotFoundResponse> { controller.getOne(context, "testId") }
        verify { context wasNot Called }
    }

    @Test
    internal fun `test delete returns nothing`() {
        assertThrows<MethodNotAllowedResponse> { controller.delete(context, "testId") }
    }

    @Test
    internal fun `test update not implemented`() {
        assertThrows<MethodNotAllowedResponse> { controller.update(context, "testId") }
    }
}