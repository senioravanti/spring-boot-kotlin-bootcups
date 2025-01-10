package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.ProductEntity

data class ProductDto(
    val id: Short?,
    @NotBlank(message = "{product-entity.name.not-blank}")
    @Size(min = 3, max = 512, message = "{product-entity.name.size}")
    val name: String,
    val description: String?
) {
    fun toEntity() = ProductEntity().apply{
        this.id = this@ProductDto.id
        this.name = this@ProductDto.name
        this.description = this@ProductDto.description
    }

    companion object {
        fun of(productEntity: ProductEntity) = ProductDto(
            productEntity.id,
            productEntity.name,
            productEntity.description
        )
    }
}
