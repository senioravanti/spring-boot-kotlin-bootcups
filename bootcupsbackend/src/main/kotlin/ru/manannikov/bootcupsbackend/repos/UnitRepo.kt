package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import ru.manannikov.bootcupsbackend.entities.UnitEntity

interface UnitRepo : JpaRepository<UnitEntity, Short> {
    fun findByName(name: String): UnitEntity
}