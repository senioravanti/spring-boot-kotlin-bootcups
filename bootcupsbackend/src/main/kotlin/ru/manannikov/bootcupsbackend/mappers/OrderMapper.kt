package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.*
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import ru.manannikov.bootcupsbackend.dto.OrderRequest
import ru.manannikov.bootcupsbackend.dto.OrderResponse
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.OrderMapperDecorator
import java.time.Instant

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE,

    uses = [ClientMapper::class, EmployeeMapper::class]
)
@DecoratedWith(OrderMapperDecorator::class)
@JvmDefaultWithCompatibility
interface OrderMapper {
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = ["instantToEpochSeconds"])
    fun toDto(order: OrderEntity): OrderResponse

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = ["epochSecondsToInstant"])
    fun toEntity(order: OrderRequest): OrderEntity

    @Named("instantToEpochSeconds")
    fun instantToEpochSeconds(createdAt: Instant): Long = createdAt.epochSecond

    @Named("epochSecondsToInstant")
    fun epochSecondsToInstant(createdAt: Long): Instant = Instant.ofEpochSecond(createdAt)
}