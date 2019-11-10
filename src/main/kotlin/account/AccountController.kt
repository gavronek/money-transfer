package account

import io.javalin.apibuilder.CrudHandler
import io.javalin.http.Context
import io.javalin.http.MethodNotAllowedResponse
import io.javalin.http.NotFoundResponse
import java.util.*


class AccountController(private val store: AccountStore) : CrudHandler {
    override fun create(ctx: Context) {
        val accountRequest = ctx.body<AccountCreateCommand>()
        val account = Account(UUID.randomUUID().toString(), accountRequest.balance, accountRequest.currency)
        ctx.status(201).json(store.create(account))
    }

    override fun getAll(ctx: Context) {
        ctx.json(store.getAll())
    }

    override fun getOne(ctx: Context, resourceId: String) {
        val account = store.getOne(resourceId) ?: throw NotFoundResponse()
        ctx.json(account)
    }

    override fun delete(ctx: Context, resourceId: String) {
        throw MethodNotAllowedResponse(details = mapOf())
    }

    override fun update(ctx: Context, resourceId: String) {
        throw MethodNotAllowedResponse(details = mapOf())
    }

}