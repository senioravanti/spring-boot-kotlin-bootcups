package ru.manannikov.bootcupsbackend.services.impl

import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbackend.entities.BonusCardEntity
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.BonusCardRepo
import ru.manannikov.bootcupsbackend.services.BonusCardService

@Service("bonusCardService")
class BonusCardServiceImpl(
    private val bonusCardRepo: BonusCardRepo
) : BonusCardService {
    override fun findByClientId(clientId: Long): BonusCardEntity = bonusCardRepo.findByClientId(clientId) ?: throw NotFoundException(null, BonusCardEntity::class.java)
}