package ru.manannikov.bootcupsbot.dto


import jakarta.validation.constraints.Positive
import java.math.BigDecimal

class BonusCardDto {
    @Positive
    lateinit var amount: BigDecimal
    @Positive
    var discountPercent: Short = 5
}
