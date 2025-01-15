package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import java.math.BigDecimal

data class OrderItemResponse(
    val id: Long,

    val quantity: Short,
    val purchase: BigDecimal,

    @get:Valid
    val menuItem: MenuItemResponse,

    val parentOrderId: Int
)