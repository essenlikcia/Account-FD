package com.account.account.dto

import java.math.BigDecimal

data class createAccountRequest(
    val customerId: String,
    val initialCredit: BigDecimal
)
