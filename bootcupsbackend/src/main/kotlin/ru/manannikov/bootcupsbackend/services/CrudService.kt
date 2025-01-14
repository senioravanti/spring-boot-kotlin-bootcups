package ru.manannikov.bootcupsbackend.services

interface CrudService<T, ID> {
    fun findById(id: ID): T
    fun save(entity: T)
    fun update(id: ID, entity: T)
    fun deleteById(id: ID)
}