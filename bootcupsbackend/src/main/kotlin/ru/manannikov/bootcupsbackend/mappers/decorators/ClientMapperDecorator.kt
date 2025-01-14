package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import ru.manannikov.bootcupsbackend.dto.ClientRequest
import ru.manannikov.bootcupsbackend.entities.ClientEntity
import ru.manannikov.bootcupsbackend.enums.RegistrationState
import ru.manannikov.bootcupsbackend.mappers.ClientMapper
import ru.manannikov.bootcupsbackend.services.BonusCardService

abstract class ClientMapperDecorator : ClientMapper {
    @set:Autowired
    open lateinit var delegate: ClientMapper

    @set:Autowired
    lateinit var bonusCardService: BonusCardService

    override fun toEntity(client: ClientRequest): ClientEntity = delegate.toEntity(client).apply {
        if (client.registrationState == RegistrationState.REGISTERED) {
            bonusCard = bonusCardService.findByClientId(client.id!!)
        }
    }
}