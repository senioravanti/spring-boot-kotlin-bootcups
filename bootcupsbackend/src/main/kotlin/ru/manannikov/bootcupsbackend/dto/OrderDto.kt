package ru.manannikov.bootcupsbackend.dto

import java.math.BigDecimal

data class OrderDto(
    val id: Int?,

    val totalAmount: BigDecimal,
    val discountAmount: BigDecimal,

    val createdAt: Long,

    val status: String,

    val client: ClientDto?,
    val employee: EmployeeDto
) {}
