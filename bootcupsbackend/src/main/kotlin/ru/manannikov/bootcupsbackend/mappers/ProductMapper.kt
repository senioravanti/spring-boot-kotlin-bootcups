package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.DecoratedWith
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import org.mapstruct.ReportingPolicy
import ru.manannikov.bootcupsbackend.dto.ProductRequest
import ru.manannikov.bootcupsbackend.dto.ProductResponse
import ru.manannikov.bootcupsbackend.entities.ProductEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.ProductMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = [MenuItemMapper::class]
)
@DecoratedWith(ProductMapperDecorator::class)
interface ProductMapper {
    fun toDto(productEntity: ProductEntity): ProductResponse
    fun toEntity(productRequest: ProductRequest): ProductEntity
}