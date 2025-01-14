package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import ru.manannikov.bootcupsbackend.dto.UnitDto
import ru.manannikov.bootcupsbackend.entities.UnitEntity

@Mapper(
    componentModel = SPRING,
)
interface UnitMapper {
    fun toEntity(unitDto: UnitDto): UnitEntity
    fun toDto(unitEntity: UnitEntity): UnitDto
}