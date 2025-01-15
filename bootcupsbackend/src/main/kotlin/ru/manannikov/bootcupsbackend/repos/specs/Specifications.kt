package ru.manannikov.bootcupsbackend.repos.specs

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.apache.logging.log4j.LogManager
import org.springframework.data.jpa.domain.Specification
import ru.manannikov.bootcupsbackend.entities.*
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.EMPLOYEE_ROLE_NAME
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_TOPPING
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PRODUCT_NAME
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MAX
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MIN
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_CREATED_AFTER
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_CREATED_BEFORE
import ru.manannikov.bootcupsbackend.utils.*
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.snakeToCamelCase
import sun.jvm.hotspot.oops.CellTypeState.value
import java.math.BigDecimal

object Specifications {
    private val logger = LogManager.getLogger(Specifications::class.java)

    /**
     * Возвращает каждый раз новый экземпляр анонимной реализации Specification
     */
    fun menuItemDefaultFilter(
        filter: Map<String, Any>
    ): Specification<MenuItemEntity> = Specification {
        root: Root<MenuItemEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.conjunction()

        filter.forEach { (key, value) ->
            try {
                when (key) {
                    PRODUCT_NAME -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("название товара"))
                        val menuItemsProductsJoin = root.join<MenuItemEntity, ProductEntity>("product")
                        val likePredicate = cb.like(menuItemsProductsJoin.get("name"), "%$value%")
                        criteria = cb.and(criteria, likePredicate)
                    }
                    MENU_ITEM_PRICE_MIN -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("минимальная цена товара"))
                        criteria = cb.and(
                            criteria,
                            cb.greaterThanOrEqualTo(root.get<BigDecimal>("price"), BigDecimal(value))
                        )
                    }
                    MENU_ITEM_PRICE_MAX -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("максимальная цена товара"))
                        criteria = cb.and(
                            criteria,
                            cb.lessThanOrEqualTo(root.get<BigDecimal>("price"), BigDecimal(value))
                        )
                    }
                    MENU_ITEM_MAKES_MIN -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("минимальный объем порции"))
                        criteria = cb.and(
                            criteria,
                            cb.greaterThanOrEqualTo(root.get<Short>("makes"), value.toShort())
                        )
                    }
                    MENU_ITEM_MAKES_MAX -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("максимальный объем порции"))
                        criteria = cb.and(
                            criteria,
                            cb.lessThanOrEqualTo(root.get<Short>("makes"), value.toShort())
                        )
                    }
                    MENU_ITEM_TOPPING -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("топпинг"))
                        criteria = cb.and(
                            criteria,
                            cb.like(root.get("topping"), "%$value%")
                        )
                    }
                    CATEGORY -> {
                        if (value !is List<*>) throw IllegalArgumentException(createErrorMessage("категории", "List<String>"))
                        val categories: List<String> = value.filterIsInstance<String>()
                        if (categories.isEmpty()) throw IllegalArgumentException("Список категорий должен содержать хотя бы одну категорию")

                        criteria = cb.and(
                            criteria,
                            menuItemCategoryFilter(root, cq, cb, categories)
                        )
                    }
                }
            } catch (ex: IllegalArgumentException) {
                logger.error(ex)
            } catch (ex: NumberFormatException) {
                logger.error("Ошибка при преобразовании строки в BigDecimal, вещественное число должно содержать точку, а не запятую:\n{}", ex.toString())
            }
        }

        criteria
    }

    private fun menuItemCategoryFilter(
        root: Root<MenuItemEntity>,
        cq: CriteriaQuery<*>?,
        cb: CriteriaBuilder,
        categories: List<String>
    ): Predicate {
        var criteria = cb.disjunction()

        categories.forEach {
            category ->

            val categorySubquery = cq!!.subquery(Short::class.java)
            val categoryRoot = categorySubquery.from(CategoryEntity::class.java)
            categorySubquery
                .select(categoryRoot.get<Short>("id"))
                .where(cb.equal(categoryRoot.get<String>("key"), category))

            val productSubquery = cq.subquery(Short::class.java)
            val productRoot = productSubquery.from(ProductEntity::class.java)
            productSubquery
                .select(productRoot.get<Short>("id"))
                .where(
                    cb.equal(
                        productRoot.join<ProductEntity, CategoryEntity>("categories").get<Short>("id"),
                        categorySubquery
                    )
                )

            criteria = cb.or(
                criteria,
                cb.`in`(root.get<ProductEntity>("product").get<Short>("id")).value(productSubquery)
            )
        }

        return criteria
    }

    /**
     * Фильтруем сотрудников по занимаемой должности
     */
    fun employeeFilter(
        filter: Map<String, String>
    ): Specification<EmployeeEntity> = Specification {
        root: Root<EmployeeEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.conjunction()

        filter.forEach {
            (key, value) ->
            when (key) {
                EMPLOYEE_LAST_NAME, EMPLOYEE_FIRST_NAME, EMPLOYEE_MIDDLE_NAME -> {
                    criteria = cb.and(
                        criteria,
                        cb.like(root.get(snakeToCamelCase(key)), "%$value%")
                    )
                }
                EMPLOYEE_ROLE_NAME -> {
                    val employeesRoleJoin = root.join<EmployeeEntity, RoleEntity>("role")
                    criteria = cb.and(
                        criteria,
                        cb.equal(employeesRoleJoin.get<String>("key"), value)
                    )
                }
            }
        }

        criteria
    }

    fun orderFilter(
        filter: Map<String, String>
    ): Specification<OrderEntity> = Specification { root: Root<OrderEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.conjunction()

        filter.forEach {
            (key, value) ->
            when (key) {
                EMPLOYEE_LAST_NAME, EMPLOYEE_FIRST_NAME, EMPLOYEE_MIDDLE_NAME -> {
                    val orderEmployeeJoin = root.join<OrderEntity, EmployeeEntity>("employee")
                    criteria = cb.and(
                        criteria,
                        cb.like(orderEmployeeJoin.get(snakeToCamelCase(key)), "%$value%")
                    )
                }
                EMPLOYEE_EMAIL -> {
                    val orderEmployeeJoin = root.join<OrderEntity, EmployeeEntity>("employee")
                    criteria = cb.and(
                        criteria,
                        cb.equal(
                            orderEmployeeJoin.get<String>(
                                snakeToCamelCase(key.substringAfter("_"))
                            ),
                            value)
                    )
                }
                CLIENT_NAME -> {

                }
                CLIENT_EMAIL -> {

                }
                ORDER_AMOUNT_MAX -> {

                }
                ORDER_AMOUNT_MIN -> {

                }
                ORDER_CREATED_BEFORE -> {

                }
                ORDER_CREATED_AFTER -> {

                }
            }
        }

        criteria
    }

    private fun createErrorMessage(fieldName: String, typeName: String = "String"): String = "Поле \"$fieldName\" должно иметь тип $typeName"
}