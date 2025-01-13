package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity
import java.math.BigDecimal

data class OrderItemDto(
    val id: Long?,

    @Size(min = 1, max = 50, message = "{order-item-entity.quantity.size}")
    val quantity: Short,
    val purchase: BigDecimal,

    @Valid
    val menuItem: MenuItemDto
)
{
    fun toEntity() = OrderItemEntity().apply {
        this.id = this@OrderItemDto.id

        this.quantity = this@OrderItemDto.quantity
        this.purchase = this@OrderItemDto.purchase

        this.menuItem = this@OrderItemDto.menuItem.toEntity()
    }

    companion object {
        fun of(orderItemEntity: OrderItemEntity) = OrderItemDto(
            id = orderItemEntity.id,

            quantity = orderItemEntity.quantity,
            purchase = orderItemEntity.purchase,

            menuItem = MenuItemDto.of(orderItemEntity.menuItem)
        )
    }
}
