package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import org.mapstruct.ReportingPolicy
import ru.manannikov.bootcupsbackend.dto.BonusCardDto
import ru.manannikov.bootcupsbackend.entities.BonusCardEntity

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BonusCardMapper {
    fun toDto(bonusCard: BonusCardEntity): BonusCardDto
}