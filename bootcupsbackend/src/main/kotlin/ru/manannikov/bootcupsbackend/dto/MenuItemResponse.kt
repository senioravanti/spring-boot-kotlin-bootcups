package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import java.math.BigDecimal

data class MenuItemResponse(
    val id: Int,

    val makes: Short,

    val price: BigDecimal,

    val topping: String?,
    val imageUri: String?,

    @get:Valid
    val product: ProductResponse,
    @get:Valid
    val unit: UnitDto
)
