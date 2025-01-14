package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.DecoratedWith
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import org.mapstruct.ReportingPolicy
import ru.manannikov.bootcupsbackend.dto.MenuItemRequest
import ru.manannikov.bootcupsbackend.dto.MenuItemResponse
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.MenuItemMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = [UnitMapper::class, ProductMapper::class]
)
@DecoratedWith(MenuItemMapperDecorator::class)
interface MenuItemMapper {
    fun toDto(menuItem: MenuItemEntity): MenuItemResponse
    fun toEntity(menuItem: MenuItemRequest): MenuItemEntity
}