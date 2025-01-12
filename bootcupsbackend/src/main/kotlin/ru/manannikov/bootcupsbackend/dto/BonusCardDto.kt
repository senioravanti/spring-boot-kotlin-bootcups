package ru.manannikov.bootcupsbackend.dto

import java.math.BigDecimal

data class BonusCardDto(
    val amount: BigDecimal,
    val discountPercent: Short
) {}
