package com.coelhocaique.finance.core.domain.dto

import java.math.BigDecimal

data class DiscountDTO (
        val amount: BigDecimal?,
        val description: String?
)