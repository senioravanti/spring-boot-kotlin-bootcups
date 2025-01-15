package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import ru.manannikov.bootcupsbackend.dto.OrderItemRequest
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity
import ru.manannikov.bootcupsbackend.mappers.OrderItemMapper
import ru.manannikov.bootcupsbackend.services.MenuItemService
import ru.manannikov.bootcupsbackend.services.OrderService

abstract class OrderItemMapperDecorator : OrderItemMapper {
    @set:Autowired
    open lateinit var delegate: OrderItemMapper

    @set:Autowired
    lateinit var orderService: OrderService
    @set:Autowired
    lateinit var menuItemService: MenuItemService

    override fun toEntity(orderItem: OrderItemRequest): OrderItemEntity = delegate.toEntity(orderItem).apply {
        menuItem = menuItemService.findById(orderItem.menuItemId)
        orderItem.parentOrderId ?.let {
            parentOrder = orderService.findById(orderItem.parentOrderId)
        }
    }
}