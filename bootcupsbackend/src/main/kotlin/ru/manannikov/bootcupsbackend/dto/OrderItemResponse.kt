package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity
import java.math.BigDecimal

data class OrderItemResponse(
    val id: Long?,

    val quantity: Short,
    val purchase: BigDecimal,

    @field:Valid
    val menuItem: MenuItemResponse
)
{
    companion object {
        fun of(orderItemEntity: OrderItemEntity) = OrderItemResponse(
            id = orderItemEntity.id,

            quantity = orderItemEntity.quantity,
            purchase = orderItemEntity.purchase,

            menuItem = MenuItemResponse.of(orderItemEntity.menuItem)
        )
    }
}
