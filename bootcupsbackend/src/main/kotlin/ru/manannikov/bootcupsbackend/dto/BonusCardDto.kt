package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import java.math.BigDecimal

data class BonusCardDto(
    @field:DecimalMin("0.00")
    val amount: BigDecimal,
    @field:Min(0)
    val discountPercent: Short
)
