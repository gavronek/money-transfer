package account

import io.javalin.http.BadRequestResponse
import io.javalin.http.NotFoundResponse
import transaction.Transaction


open class AccountStore(private val db: MutableMap<String, Account>) {

    fun create(account: Account) : Account {
        db[account.id] = account
        return account
    }

    fun getAll() : List<Account> = db.values.toList()

    fun getOne(accountId: String) : Account? = db[accountId]


    @Synchronized
    fun execute(transaction: Transaction) {
        val sourceAccount = getOne(transaction.source)
            ?: throw NotFoundResponse("Source account does not exist")
        if (sourceAccount.currency != transaction.currency)
            throw BadRequestResponse("Source account and transaction has conflicting currency")
        if (sourceAccount.balance < transaction.amount)
            throw BadRequestResponse("The balance of the source account is not enough to fulfill transaction")

        val destinationAccount = getOne(transaction.destination)
            ?: throw NotFoundResponse("Destination account does not exist")
        if (destinationAccount.currency != transaction.currency)
            throw BadRequestResponse("Destination account and transaction has conflicting currency")

        destinationAccount.plus(transaction.amount)
        sourceAccount.minus(transaction.amount)
    }
}