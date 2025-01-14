package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class OrderItemRequest(
    val id: Long?,

    @field:Size(min = 1, max = 50, message = "{order-item-entity.quantity.size}")
    val quantity: Short,

    @field:Min(1)
    val orderId: Int,
    @field:Min(1)
    val menuItemId: Int
)
