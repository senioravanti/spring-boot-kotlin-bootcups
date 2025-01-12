package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.manannikov.bootcupsbackend.TestcontainersTest

@DataJpaTest
class ProductRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var productRepo: ProductRepo

    @Test
    fun testFindAll() {
        val products = productRepo.findAll()
        logger.info(listMenuItems(products))
        logger.info(products.size)
    }

    @Test
    fun testFindByName() {
        val productEntity = productRepo.findByName("Цикорий")
        logger.info(productEntity)
        logger.info(productEntity.categories)
    }

    companion object {
        private val logger = LogManager.getLogger(ProductRepoTests::class.java)
    }
}