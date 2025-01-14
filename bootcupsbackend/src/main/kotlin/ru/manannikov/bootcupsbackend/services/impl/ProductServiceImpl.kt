package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.entities.ProductEntity
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.ProductRepo
import ru.manannikov.bootcupsbackend.services.ProductService
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity

@Transactional(readOnly = true)
@Service("productService")
class ProductServiceImpl(
    private val productRepo: ProductRepo
) : ProductService {
    override fun findById(id: Short): ProductEntity {
        if (productRepo.count() == 0L) throw NotFoundException(ProductEntity::class.java)

        return productRepo.findById(id).orElseThrow {
            NotFoundException(id.toString(), MenuItemEntity::class.java)
        }
    }

    @Transactional
    override fun save(entity: ProductEntity) {
        if (entity.id != null) throw EntityAlreadyExistsException(
            tableNameFromEntity(ProductEntity::class)
        )

        val savedProduct = productRepo.save(entity)
        logger.debug("Пищевой продукт успешно добавлен в БД:\n{}", savedProduct)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Short, entity: ProductEntity) {
        var existingProduct = findById(id).apply {
            name = entity.name
            description = entity.description
            categories = entity.categories
        }
        existingProduct = productRepo.save(existingProduct)
        logger.debug("Данные пищевого продукта с идентификатором {} успешно обновлены:\n{}", id, existingProduct)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Short) {
        findById(id)
        productRepo.deleteById(id)
        logger.debug("Пищевой продукт с идентификатором {} успешно удален", id)
    }

    companion object {
        private val logger = LogManager.getLogger(ProductServiceImpl::class.java)
    }
}