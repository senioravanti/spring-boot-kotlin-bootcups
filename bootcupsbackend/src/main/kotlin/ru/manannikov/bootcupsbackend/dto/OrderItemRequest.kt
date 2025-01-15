package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range

data class OrderItemRequest(
    val id: Long?,

    @get:Range(min = 1, max = 50, message = "{order-item-entity.quantity.size}")
    val quantity: Short,

    @get:Min(1)
    val menuItemId: Int,

    @get:Min(1)
    val parentOrderId: Int? = null
)
