package ru.manannikov.bootcupsbackend.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.RoleRepo
import ru.manannikov.bootcupsbackend.services.DictionaryService

@Transactional(readOnly = true)
@Service("roleService")
class RoleDictionaryServiceImpl(
    private val roleRepo: RoleRepo
)
    : DictionaryService<RoleEntity>
{
    override fun findAll(): List<RoleEntity> = roleRepo.findAll()

    override fun findByKey(key: String): RoleEntity = roleRepo.findByKey(key) ?: throw NotFoundException(key, RoleEntity::class.java)
}