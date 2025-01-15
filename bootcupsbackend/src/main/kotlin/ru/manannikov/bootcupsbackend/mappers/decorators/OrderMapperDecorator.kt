package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import ru.manannikov.bootcupsbackend.dto.OrderRequest
import ru.manannikov.bootcupsbackend.dto.OrderResponse
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import ru.manannikov.bootcupsbackend.mappers.OrderMapper
import ru.manannikov.bootcupsbackend.services.ClientService
import ru.manannikov.bootcupsbackend.services.EmployeeService
import ru.manannikov.bootcupsbackend.services.OrderItemService
import ru.manannikov.bootcupsbackend.utils.MiscellaneousMapper
import java.time.Instant

abstract class OrderMapperDecorator : OrderMapper {
    @set:Autowired
    open lateinit var delegate: OrderMapper

    @set:Autowired
    lateinit var miscellaneousMapper: MiscellaneousMapper

    @set:Autowired
    lateinit var clientService: ClientService
    @set:Autowired
    lateinit var employeeService: EmployeeService
    @set:Autowired
    lateinit var orderItemService: OrderItemService

    override fun toDto(order: OrderEntity): OrderResponse = delegate.toDto(order).apply {
        statusDto = miscellaneousMapper.toFieldEnumDto(order.status)
        orderItemCount = orderItemService.countByOrderId(order.id!!)
    }
    override fun toEntity(order: OrderRequest): OrderEntity = delegate.toEntity(order).apply {
        status = OrderStatus.valueOf(order.statusString.uppercase())

        client = order.clientId ?.let {
            clientService.findById(it)
        }
        employee = employeeService.findById(order.employeeId)
    }
}