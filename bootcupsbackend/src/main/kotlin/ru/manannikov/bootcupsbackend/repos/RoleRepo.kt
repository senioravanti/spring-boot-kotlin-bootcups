package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.repository.ListCrudRepository
import ru.manannikov.bootcupsbackend.entities.RoleEntity

interface RoleRepo : ListCrudRepository<RoleEntity, Short> {
    fun findByKey(key: String): RoleEntity?
}