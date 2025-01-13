package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.repository.ListCrudRepository
import ru.manannikov.bootcupsbackend.entities.CategoryEntity

interface CategoryRepo : ListCrudRepository<CategoryEntity, Short> {
    fun findByKey(key: String): CategoryEntity?
}