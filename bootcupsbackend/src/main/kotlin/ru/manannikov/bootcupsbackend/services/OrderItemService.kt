package ru.manannikov.bootcupsbackend.services

import ru.manannikov.bootcupsbackend.entities.OrderItemEntity

interface OrderItemService {
    fun countByOrderId(orderId: Int): Int
    fun findByOrderId(orderId: Int): List<OrderItemEntity>
}