import account.Account
import io.javalin.Javalin
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import transaction.Transaction

class ApplicationRunner : BeforeEachCallback, BeforeAllCallback, AfterAllCallback {
    private lateinit var app: Javalin
    private lateinit var accountDb : MutableMap<String, Account>
    private lateinit var transactionDb: MutableMap<String, Transaction>

    override fun beforeAll(context: ExtensionContext?) {
        val (app, accountDb, transactionDb) = Application(8000).init()
        this.app = app
        this.accountDb = accountDb
        this.transactionDb = transactionDb
    }

    override fun beforeEach(context: ExtensionContext?) {
        accountDb.clear()
        transactionDb.clear()
    }

    override fun afterAll(context: ExtensionContext?) {
        app.stop()
    }
}