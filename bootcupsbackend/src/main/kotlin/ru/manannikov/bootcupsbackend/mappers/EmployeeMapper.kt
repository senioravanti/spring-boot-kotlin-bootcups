package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.*
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import ru.manannikov.bootcupsbackend.dto.EmployeeRequest
import ru.manannikov.bootcupsbackend.dto.EmployeeResponse
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.EmployeeMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(EmployeeMapperDecorator::class)
interface EmployeeMapper {
    @Mapping(source = "role", target = "role", ignore = true)
    fun toDto(employee: EmployeeEntity): EmployeeResponse
    fun toEntity(employee: EmployeeRequest): EmployeeEntity
}