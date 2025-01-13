package ru.manannikov.bootcupsbackend.entities

import jakarta.persistence.Table
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity
import java.math.BigDecimal
import java.time.Instant

class EntityEqualityTests {

    lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        val validationFactory = Validation.buildDefaultValidatorFactory()
        validator = validationFactory.validator
    }

    @Test
    fun testTableName() {
        val expectedTableName = "menu_items"
        assertAll (
            { assertEquals(expectedTableName, tableNameFromEntity(MenuItemEntity::class)) },
            { assertEquals(expectedTableName, MenuItemEntity::class.java.getDeclaredAnnotationsByType(Table::class.java)[0].name) }
        )
    }

    @Test
    fun testOrdersEquality() {
        val creationMoment = Instant.now()
        val order1 = OrderEntity().apply {
            createdAt = creationMoment
            totalAmount = BigDecimal("1000.0")
            discountAmount = BigDecimal("100.50")
        }
        val order2 = OrderEntity().apply {
            id = 100
            createdAt = creationMoment
            totalAmount = BigDecimal("1000.0")
            discountAmount = BigDecimal("100.50")
        }

        assert(order1, order2)
    }

    @Test
    fun testClientsEquality() {
        val client1 = ClientEntity().apply {
            phoneNumber = "+7 900 600 21-14"
        }
        val client2 = ClientEntity().apply {
            name = "Борька"
            phoneNumber = "+7 900 600 21-14"
        }

        assert(client1, client2)
    }

    @Test
    fun testMenuItemsEquality() {
        val product1 = ProductEntity().apply {
            id = 1
            name = "Ананас"
        }

        val menuItem1 = MenuItemEntity().apply {
            product = product1
            price = BigDecimal("150.00")
        }
        val menuItem2 = MenuItemEntity().apply {
            product = product1
            price = BigDecimal("150.00")
        }

        assert(menuItem1, menuItem2)
    }

    companion object {
        private val logger = LogManager.getLogger(EntityEqualityTests::class.java)

        fun assert(entity1: Any, entity2: Any) {
            assertEquals(entity1, entity2)
            assertEquals(entity1.hashCode(), entity2.hashCode())
        }
    }
}