package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.*
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import ru.manannikov.bootcupsbackend.dto.OrderItemRequest
import ru.manannikov.bootcupsbackend.dto.OrderItemResponse
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.entities.OrderItemEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.OrderItemMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = [MenuItemMapper::class]
)
@DecoratedWith(OrderItemMapperDecorator::class)
@JvmDefaultWithCompatibility
interface OrderItemMapper {
    @Mapping(source = "parentOrder", target = "parentOrderId", qualifiedByName = ["orderToOrderId"])
    fun toDto(orderItem: OrderItemEntity): OrderItemResponse
    fun toEntity(orderItem: OrderItemRequest): OrderItemEntity

    @Named("orderToOrderId")
    fun orderToOrderId(parentOrder: OrderEntity): Int = parentOrder.id!!
}