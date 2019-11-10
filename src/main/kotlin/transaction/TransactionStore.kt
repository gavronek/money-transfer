package transaction


class TransactionStore(private val db: MutableMap<String, Transaction>) {

    fun create(transaction: Transaction) : Transaction {
        db[transaction.id] = transaction
        return transaction
    }

    fun getAll() : List<Transaction> = db.values.toList()

    fun getOne(accountId: String) : Transaction? = db[accountId]
}