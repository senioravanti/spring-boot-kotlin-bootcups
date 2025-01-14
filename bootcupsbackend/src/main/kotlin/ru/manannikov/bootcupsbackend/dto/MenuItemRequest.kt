package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import java.math.BigDecimal

data class MenuItemRequest(
    override val id: Int?,

    @field:Range(min = 5, max = 5000, message = "{menu-item-entity.makes.size}")
    override val makes: Short,

    @field:DecimalMin("5.00")
    @field:Digits(integer = 6, fraction = 2)
    override val price: BigDecimal,

    @field:Size(max = 128, message = "{menu-item-entity.topping.size}")
    override val topping: String?,
    @field:Size(max = 1024)
    override val imageUri: String?,

    @field:Min(1)
    val productId: Short,
    @field:Min(1)
    val unitId: Short
) : MenuItemDto {
    companion object {
        fun of(menuItemEntity: MenuItemEntity) = MenuItemRequest(
            id = menuItemEntity.id,

            makes = menuItemEntity.makes,
            price = menuItemEntity.price,

            topping = menuItemEntity.topping,
            imageUri = menuItemEntity.imageUri,

            productId = menuItemEntity.product.id!!,
            unitId = menuItemEntity.unit.id!!
        )
    }
}
