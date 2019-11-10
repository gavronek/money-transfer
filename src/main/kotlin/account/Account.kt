package account

import io.javalin.http.BadRequestResponse
import java.math.BigDecimal
import java.util.*

data class Account(val id: String, var balance: BigDecimal, val currency: Currency) {
    fun plus(amount: BigDecimal) = this.run { balance = balance.plus(amount) }
    fun minus(amount: BigDecimal) = this.run { balance = balance.minus(amount) }
}

data class AccountCreateCommand(val balance: BigDecimal, val currency: Currency) {
    init {
        if (balance < BigDecimal.ZERO) throw BadRequestResponse("Can't open account with negative balance")
    }
}