package com.account.account.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class createAccountRequest(
    @field:NotBlank(message = "Customer id is required")
    val customerId: String,
    @field:Min(0, message = "Initial credit must be at least 0")
    val initialCredit: BigDecimal
)
