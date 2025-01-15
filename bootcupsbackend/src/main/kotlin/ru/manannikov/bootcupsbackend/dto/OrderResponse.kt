package ru.manannikov.bootcupsbackend.dto

import java.math.BigDecimal

class OrderResponse(
    val id: Int,

    val totalAmount: BigDecimal,
    val discountAmount: BigDecimal,

    val client: ClientResponse?,
    val employee: EmployeeResponse,

    val createdAt: Long
) {
    lateinit var statusDto: FieldEnumDto
    var orderItemCount: Int? = null

    override fun toString(): String {
        return "OrderResponse(id=$id, totalAmount=$totalAmount, discountAmount=$discountAmount, client=$client, employee=$employee, createdAt=$createdAt, statusDto=$statusDto, orderItemCount=$orderItemCount)"
    }
}
