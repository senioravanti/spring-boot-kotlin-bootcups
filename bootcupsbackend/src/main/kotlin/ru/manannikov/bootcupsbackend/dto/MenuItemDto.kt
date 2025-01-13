package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import java.math.BigDecimal

data class MenuItemDto(
    val id: Int?,

    @Size(min = 5, max = 5000, message = "{menu-item-entity.makes.size}")
    val makes: Short,
    val price: BigDecimal,

    @Size(max = 128, message = "{menu-item-entity.topping.size}")
    val topping: String?,
    @Size(max = 1024)
    val imageUri: String?,

    @Valid
    val product: ProductDto,
    @Valid
    val unit: UnitDto
) {
    fun toEntity() = MenuItemEntity().apply {
        this.id = this@MenuItemDto.id

        this.makes = this@MenuItemDto.makes
        this.price = this@MenuItemDto.price

        this.topping = this@MenuItemDto.topping
        this.imageUri = this@MenuItemDto.imageUri

        this.product = this@MenuItemDto.product.toEntity()
        this.unit = this@MenuItemDto.unit.toEntity()
    }

    companion object {
        fun of(menuItemEntity: MenuItemEntity) = MenuItemDto(
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
