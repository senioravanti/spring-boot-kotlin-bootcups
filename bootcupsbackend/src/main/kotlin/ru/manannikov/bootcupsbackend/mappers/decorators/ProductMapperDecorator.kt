package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import ru.manannikov.bootcupsbackend.dto.ProductRequest
import ru.manannikov.bootcupsbackend.entities.CategoryEntity
import ru.manannikov.bootcupsbackend.entities.ProductEntity
import ru.manannikov.bootcupsbackend.mappers.ProductMapper
import ru.manannikov.bootcupsbackend.services.DictionaryService

abstract class ProductMapperDecorator : ProductMapper {
    @set:Autowired
    open lateinit var delegate: ProductMapper

    @set:Qualifier("categoryService")
    @set:Autowired
    lateinit var categoryService: DictionaryService<CategoryEntity>

    override fun toEntity(productRequest: ProductRequest): ProductEntity = delegate.toEntity(productRequest).apply {
        categories = productRequest.categoryKeys.map { categoryService.findByKey(it) } .toSet()
    }
}