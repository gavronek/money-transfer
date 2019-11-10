package transaction

import account.AccountStore
import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import io.javalin.http.MethodNotAllowedResponse
import io.javalin.http.NotFoundResponse
import java.util.*


class TransactionController(
    private val transactionStore: TransactionStore,
    private val accountStore: AccountStore
) : CrudHandler {

    override fun create(ctx: Context) {
        val transactionToCreate = ctx.body<TransactionCreateCommand>()

        val transaction = Transaction(
            UUID.randomUUID().toString(),
            transactionToCreate.source,
            transactionToCreate.destination,
            transactionToCreate.amount,
            transactionToCreate.currency
        )

        transactionStore.create(transaction)
        accountStore.execute(transaction)

        ctx.status(201).json(transaction)
    }

    override fun getAll(ctx: Context) {
        ctx.json(transactionStore.getAll())
    }

    override fun getOne(ctx: Context, resourceId: String) {
        val transaction = transactionStore.getOne(resourceId) ?: throw NotFoundResponse()
        ctx.json(transaction)
    }

    override fun delete(ctx: Context, resourceId: String) {
        throw MethodNotAllowedResponse(details = mapOf())
    }

    override fun update(ctx: Context, resourceId: String) {
        throw MethodNotAllowedResponse(details = mapOf())
    }

}