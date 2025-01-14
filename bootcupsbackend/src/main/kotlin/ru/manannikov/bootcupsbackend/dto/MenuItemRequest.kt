package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range
import java.math.BigDecimal

data class MenuItemRequest(
    val id: Int?,

    @get:Range(min = 5, max = 5000, message = "{menu-item-entity.makes.size}")
    val makes: Short,

    @get:DecimalMin("5.00")
    @get:Digits(integer = 6, fraction = 2)
    val price: BigDecimal,

    @get:Size(max = 128, message = "{menu-item-entity.topping.size}")
    val topping: String?,
    @get:Size(max = 1024)
    val imageUri: String?,

    @get:Min(1)
    val productId: Short,
    @get:Min(1)
    val unitId: Short
)
