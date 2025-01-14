package ru.manannikov.bootcupsbackend.dto

import java.math.BigDecimal

data class OrderRequest(
    val id: Int?,

    val totalAmount: BigDecimal,
    val discountAmount: BigDecimal,

    val createdAt: Long,

    val statusString: String,

    val clientId: Long?,
    val employeeId: Int
)