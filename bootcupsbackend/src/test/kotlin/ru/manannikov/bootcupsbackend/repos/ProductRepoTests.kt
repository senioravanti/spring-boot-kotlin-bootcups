package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductRepoTests : BaseRepoTest() {
    @Autowired
    lateinit var productRepo: ProductRepo

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