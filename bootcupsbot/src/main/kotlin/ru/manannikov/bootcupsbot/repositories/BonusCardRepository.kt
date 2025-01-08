package ru.manannikov.bootcupsbot.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.manannikov.bootcupsbot.entities.BonusCardEntity

interface BonusCardRepository : JpaRepository<BonusCardEntity?, Long?>
