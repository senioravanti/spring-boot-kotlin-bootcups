package ru.manannikov.bootcupsbackend.mappers

import org.mapstruct.DecoratedWith
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import org.mapstruct.ReportingPolicy
import ru.manannikov.bootcupsbackend.dto.ClientRequest
import ru.manannikov.bootcupsbackend.dto.ClientResponse
import ru.manannikov.bootcupsbackend.entities.ClientEntity
import ru.manannikov.bootcupsbackend.mappers.decorators.ClientMapperDecorator

@Mapper(
    componentModel = SPRING,
    injectionStrategy = InjectionStrategy.SETTER,

    unmappedTargetPolicy = ReportingPolicy.IGNORE,

    uses = [BonusCardMapper::class]
)
@DecoratedWith(ClientMapperDecorator::class)
interface ClientMapper {
    fun toDto(client: ClientEntity): ClientResponse
    fun toEntity(client: ClientRequest): ClientEntity
}