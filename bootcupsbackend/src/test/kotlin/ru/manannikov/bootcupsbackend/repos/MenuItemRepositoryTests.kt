package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.data.domain.Sort.Direction

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import java.math.BigDecimal

@ActiveProfiles("test")
@DataJpaTest
@Testcontainers
@TestPropertySource("classpath:application.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuItemRepositoryTests {
    @Autowired
    lateinit var menuItemRepo: MenuItemRepository

    @Test
    fun testFindAll() {
        logger.info("Выборка всех позиций меню из таблицы menu_items")
        val menuItems = menuItemRepo.findAll(Specifications.menuItemPriceCriteria())
        listMenuItems(menuItems)
        assertEquals(57, menuItems.size)
    }

    @Test
    @DisplayName("Должен возвращать список из четырех позиций меню, отсортированных по объему порции")
    fun testFindAllByProductName() {
        logger.info("Выборка позиций меню из таблицы menu_items по заданному названию продукта")
//        val menuItems = menuItemRepo.findAllByProductName("Капучино", Sort.by(Order(Direction.ASC, "makes")))
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemProductNameCriteria(
                "Капучино"
            ),
            Sort.by(Order(Direction.ASC, "makes")
            )
        )
        listMenuItems(menuItems)
        assertEquals(4, menuItems.size)
    }

    @Test
    fun testFindAllByCriteria() {
        logger.info("Выборка позиций меню из таблицы menu_items, цена которых находится в заданном диапазоне")
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemPriceCriteria(
                null,
                BigDecimal("550.50"),
                "Капучино"
            ),
            Sort.by(Order(Direction.ASC, "price"), Order(Direction.ASC, "makes"))
        )
        listMenuItems(menuItems)
        logger.info(menuItems.size)
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemRepositoryTests::class.java)
        @Container
        private val POSTGRES_CONTAINER: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:17.2")

        fun listMenuItems(menuItems: List<MenuItemEntity>) {
            val logMessage = StringBuilder("Результат запроса\n---\n")
            menuItems.forEach {
                logMessage.append(it).append("\n")
            }
            logger.info(logMessage.append("\n---"))
        }
    }
}