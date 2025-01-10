package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.Order
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import java.math.BigDecimal
import kotlin.math.ceil


class MenuItemRepoTests : BaseRepoTest() {
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
        logger.info(listMenuItems(menuItems))
        assertEquals(57, menuItems.size)
    }

    @Test
    @DisplayName("Должен возвращать список из трех позиций меню, отсортированных по объему порции")
    fun testFindAllByDefaultFilterWithProductName() {
        logger.info("Выборка позиций меню из таблицы menu_items по заданному названию продукта")
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemDefaultFilter(
                mapOf(
                    "product_name" to "Капучино",
                    "menu_item_makes_min" to "360",
                    "menu_item_makes_max" to "360"
                )
            ),
            Sort.by(Order(Direction.ASC, "makes"))
        )
        logger.info(listMenuItems(menuItems))
        assertEquals(1, menuItems.size)
    }

    @Test
    fun testFindAllByDefaultFilter() {
        logger.info("Выборка позиций меню из таблицы menu_items, цена которых находится в заданном диапазоне")
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemDefaultFilter(
                mapOf(
                    "product_name" to "Капучино",
                    "menu_item_price_max" to BigDecimal("550.50")
                )
            ),
            Sort.by(Order(Direction.ASC, "price"), Order(Direction.ASC, "makes"))
        )
        logger.info(listMenuItems(menuItems))
        logger.info(menuItems.size)
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
            logger.info(listMenuItems(menuItemsPage.content))
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
                    mapOf("product_name" to "Капучино")
                ),
                PageRequest.of(
                    i++,
                    pageSize,
                    Sort.by(Order(Direction.ASC, "price"))
                )
            )
            logger.info(listMenuItems(menuItemsPage.content))
            logger.info("\n---\nnumber: {}, number of elements: {}, size: {}, total pages: {}\n---", menuItemsPage.number, menuItemsPage.numberOfElements, menuItemsPage.size, menuItemsPage.totalPages)

            if (menuItemsPage.isLast) break
        }
    }

    @Test
    fun testFindByCategory() {
        logger.info("Выборка всех позиций меню из таблицы menu_items, прин. заданной категории")
        val menuItems = menuItemRepo.findAllByCategory("Напитки")
        logger.info(listMenuItems(menuItems))
        logger.info(menuItems.size)
    }

    @Test
    fun testFindAllByCategoryFilter() {
        val menuItems = menuItemRepo.findAll(
            Specifications.menuItemDefaultFilter(
                mapOf(
                    "menu_item_price_min" to "100.00",
                    "categories" to listOf("Завтраки", "Выпечка", "Салаты")
                )
            )
        )
        logger.info(listMenuItems(menuItems))
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