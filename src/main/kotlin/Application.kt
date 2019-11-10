import account.Account
import account.AccountController
import account.AccountStore
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.crud
import transaction.Transaction
import transaction.TransactionController
import transaction.TransactionStore
import java.util.concurrent.ConcurrentHashMap

fun main(args: Array<String>) {
    Application(7000).init()
}

class Application(private val port: Int) {
    fun init() : Triple<Javalin, MutableMap<String, Account>, MutableMap<String, Transaction>> {
        val accountDb = ConcurrentHashMap<String, Account>()
        val transactionDb = ConcurrentHashMap<String, Transaction>()

        val accountStore = AccountStore(accountDb)
        val transactionStore = TransactionStore(transactionDb)

        val app = Javalin.create().start(port)
        app.routes {
            crud("accounts/:account-id", AccountController(accountStore))
            crud("transactions/:transaction-id", TransactionController(transactionStore, accountStore))
        }
        return Triple(app, accountDb, transactionDb)
    }
}