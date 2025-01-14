package ru.manannikov.bootcupsbackend.dto

import java.math.BigDecimal

interface MenuItemDto {
    val id: Int?

    val makes: Short
    val price: BigDecimal

    val topping: String?
    val imageUri: String?
}