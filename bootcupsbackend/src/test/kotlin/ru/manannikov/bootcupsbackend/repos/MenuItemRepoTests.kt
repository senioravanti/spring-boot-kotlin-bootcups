package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.Order
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PRODUCT_NAME
import java.math.BigDecimal
import kotlin.math.ceil

@DataJpaTest
class MenuItemRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var menuItemRepo: MenuItemRepo

    @Autowired
    lateinit var unitRepo: UnitRepo

    @Autowired
    lateinit var productRepo: ProductRepo

    @Test
    fun testFindAll() {
        logger.info("Выборка всех позиций меню из таблицы menu_items")
        val menuItems = menuItemRepo.findAll()
        logger.info(listEntities(menuItems))
        assertEquals(57, menuItems.size)
    }

    @Test
    @DisplayName("Должен возвращать список из трех позиций меню, отсортированных по объему порции")
    fun testFindAllByDefaultFilterWithProductName() {
        logger.info("Выборка позиций меню из таблицы menu_items по заданному названию продукта")
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemDefaultFilter(
                mapOf(
                    PRODUCT_NAME to "Капучино",
                    MENU_ITEM_MAKES_MIN to "360",
                    MENU_ITEM_MAKES_MAX to "360"
                )
            ),
            Sort.by(Order(Direction.ASC, "makes"))
        )
        logger.info(listEntities(menuItems))
        assertEquals(1, menuItems.size)
    }

    @Test
    fun testFindAllByDefaultFilter() {
        logger.info("Выборка позиций меню из таблицы menu_items, цена которых находится в заданном диапазоне")
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemDefaultFilter(
                mapOf(
                    PRODUCT_NAME to "Капучино",
                    MENU_ITEM_PRICE_MAX to "550.50"
                )
            ),
            Sort.by(Order(Direction.ASC, "price"), Order(Direction.ASC, "makes"))
        )
        logger.info(listEntities(menuItems))
        logger.info(menuItems.size)
        assertEquals(2, menuItems.size)
    }

    /**
     * number of elements -> фактическое количество эелментов в объекте page, например, для последней страницы этот показатель может отличаться от заданного размера страницы
     */
    @Test
    @DisplayName("Должен постранично возвращать содержимое таблицы menu_items")
    fun testFindAllByPage() {
        val pageSize = 10
        val pageCount = ceil(menuItemRepo.count() / 10.0).toInt()
        for (i in 0..pageCount) {
            val menuItemsPage = menuItemRepo.findAll(Pageable.ofSize(pageSize).withPage(i))
            logger.info(listEntities(menuItemsPage.content))
            logger.info("\n---\nnumber: {}, number of elements: {}, size: {}, total pages: {}\n---", menuItemsPage.number, menuItemsPage.numberOfElements, menuItemsPage.size, menuItemsPage.totalPages)
        }
    }

    @Test
    fun testFindAllByDefaultFilterWithPagination() {
        val pageSize = 2
        var i = 0
        while (true) {
            val menuItemsPage = menuItemRepo.findAll(
                Specifications.menuItemDefaultFilter(
                    mapOf(PRODUCT_NAME to "Капучино")
                ),
                PageRequest.of(
                    i++,
                    pageSize,
                    Sort.by(Order(Direction.ASC, "price"))
                )
            )
            logger.info(listEntities(menuItemsPage.content))
            logger.info("\n---\nnumber: {}, number of elements: {}, size: {}, total pages: {}\n---", menuItemsPage.number, menuItemsPage.numberOfElements, menuItemsPage.size, menuItemsPage.totalPages)

            if (menuItemsPage.isLast) break
        }
    }

    @Test
    fun testFindAllByCategoryFilter() {
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemDefaultFilter(
                mapOf(
                    MENU_ITEM_PRICE_MIN to "100.00",
                    CATEGORY to listOf("breakfast", "backing", "salad")
                )
            )
        )
        logger.info(listEntities(menuItems))
        logger.info(menuItems.size)
        assertEquals(6, menuItems.size)
    }

    /**
     * Метод save всегда в случае успеха возвращает тот же объект, что и был передан ему в качестве аргумента
     */
    @Test
    fun testCreateMenuItem() {
        val unitEntity = unitRepo.findByName("Миллилитр")
        val productEntity = productRepo.findByName("Маффин")

        val menuItemEntity = MenuItemEntity().apply {
            this.unit = unitEntity
            this.product = productEntity

            price = BigDecimal("100.00")
        }
        val savedMenuItemEntity = menuItemRepo.save(menuItemEntity)
        logger.info(savedMenuItemEntity)
        assertSame(menuItemEntity, savedMenuItemEntity)
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemRepoTests::class.java)
    }
}