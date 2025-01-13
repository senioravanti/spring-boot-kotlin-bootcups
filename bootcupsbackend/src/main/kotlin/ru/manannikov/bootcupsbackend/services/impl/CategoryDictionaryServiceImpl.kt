package ru.manannikov.bootcupsbackend.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.CategoryEntity
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.CategoryRepo
import ru.manannikov.bootcupsbackend.services.DictionaryService

@Transactional(readOnly = true)
@Service("categoryService")
class CategoryDictionaryServiceImpl(
    private val categoryRepo: CategoryRepo
)
    : DictionaryService<CategoryEntity>
{
    override fun findAll(): List<CategoryEntity> = categoryRepo.findAll()

    override fun findByKey(key: String): CategoryEntity = categoryRepo.findByKey(key) ?: throw NotFoundException(key, CategoryEntity::class.java)
}