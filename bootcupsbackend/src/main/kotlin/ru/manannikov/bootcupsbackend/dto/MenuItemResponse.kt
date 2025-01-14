package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import java.math.BigDecimal

data class MenuItemResponse(
    override val id: Int?,

    override val makes: Short,

    override val price: BigDecimal,

    override val topping: String?,
    override val imageUri: String?,

    @field:Valid
    val product: ProductDto,
    @field:Valid
    val unit: UnitDto
) : MenuItemDto {
    companion object {
        fun of(menuItemEntity: MenuItemEntity) = MenuItemResponse(
            menuItemEntity.id,

            menuItemEntity.makes,
            menuItemEntity.price,

            menuItemEntity.topping,
            menuItemEntity.imageUri,

            ProductDto.of(menuItemEntity.product),
            UnitDto.of(menuItemEntity.unit)
        )
    }
}
