package ru.manannikov.bootcupsbackend.services

import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderStatus

interface OrderService : CrudService<OrderEntity, Int> {
    fun findAll(orderStatus: OrderStatus): List<OrderEntity>
}