package ru.manannikov.bootcupsbackend.services

import ru.manannikov.bootcupsbackend.entities.BonusCardEntity

interface BonusCardService {
    fun findByClientId(clientId: Long): BonusCardEntity
}