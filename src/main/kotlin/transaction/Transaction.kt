package transaction

import io.javalin.http.BadRequestResponse
import java.math.BigDecimal
import java.util.*

data class Transaction(
    val id: String,
    val source: String,
    val destination: String,
    val amount: BigDecimal,
    val currency: Currency
)

data class TransactionCreateCommand(
    val source: String,
    val destination: String,
    val amount: BigDecimal,
    val currency: Currency
) {
    init {
        if (amount < BigDecimal.ZERO)
            throw BadRequestResponse("Amount of a transaction has to be positive")
    }
}