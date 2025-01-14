package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.DecoratedWith
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import org.mapstruct.ReportingPolicy
import ru.manannikov.bootcupsbackend.dto.EmployeeDto
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.EmployeeMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(EmployeeMapperDecorator::class)
interface EmployeeMapper {
    fun toDto(employee: EmployeeEntity): EmployeeDto
    fun toEntity(employee: EmployeeDto): EmployeeEntity
}