package ru.manannikov.bootcupsbot.utils

import ru.manannikov.bootcupsbot.dto.BonusCardDto
import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.entities.BonusCardEntity
import ru.manannikov.bootcupsbot.entities.ClientEntity

object Mapper {
    fun clientEntityToDto(clientEntity: ClientEntity): ClientDto {
        return ClientDto(clientEntity.id!!).apply {
            registrationState = clientEntity.registrationState

            name = clientEntity.name
            birthday = clientEntity.birthday

            email = clientEntity.email
            phoneNumber = clientEntity.phoneNumber

            clientEntity.bonusCard?.let {
                bonusCard = bonusCardEntityToDto(it)
            }
        }
    }

    fun clientEntityFromDto(clientDto: ClientDto): ClientEntity {
        return ClientEntity().apply {
            id = clientDto.chatId
            registrationState = clientDto.registrationState

            name = clientDto.name
            birthday = clientDto.birthday
            email = clientDto.email
            phoneNumber = clientDto.phoneNumber
        }
    }

    fun bonusCardEntityToDto(bonusCardEntity: BonusCardEntity): BonusCardDto {
        return BonusCardDto().apply {
            amount = bonusCardEntity.amount
            discountPercent = bonusCardEntity.discountPercent
        }
    }
}