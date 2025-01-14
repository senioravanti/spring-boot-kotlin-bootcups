package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class OrderItemRequest(
    val id: Long?,

    @get:Size(min = 1, max = 50, message = "{order-item-entity.quantity.size}")
    val quantity: Short,

    @get:Min(1)
    val menuItemId: Int
)
