package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.*
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import ru.manannikov.bootcupsbackend.dto.OrderRequest
import ru.manannikov.bootcupsbackend.dto.OrderResponse
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.OrderMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE,

    uses = [ClientMapper::class, EmployeeMapper::class]
)
@DecoratedWith(OrderMapperDecorator::class)
interface OrderMapper {
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    fun toDto(order: OrderEntity): OrderResponse
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    fun toEntity(order: OrderRequest): OrderEntity
}