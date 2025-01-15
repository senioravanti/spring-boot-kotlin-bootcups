package ru.manannikov.bootcupsbackend.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity

interface OrderItemService {
    fun countByOrderId(orderId: Int): Int

    fun findById(id: Long): OrderItemEntity

    fun findByOrderId(
        orderId: Int,
        pageRequest: Pageable
    ): Page<OrderItemEntity>

    fun save(parentOrder: OrderEntity, entity: OrderItemEntity)

    fun update(id: Long, quantity: Short)

    fun deleteById(id: Long)
}