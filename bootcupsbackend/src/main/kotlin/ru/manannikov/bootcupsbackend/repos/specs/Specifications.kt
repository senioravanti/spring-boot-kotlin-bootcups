package ru.manannikov.bootcupsbackend.repos.specs

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.apache.logging.log4j.LogManager
import org.springframework.data.jpa.domain.Specification
import ru.manannikov.bootcupsbackend.entities.CategoryEntity
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.entities.ProductEntity
import java.math.BigDecimal

object Specifications {
    private val logger = LogManager.getLogger(Specifications::class.java)

    fun menuItemDefaultFilter(
        filter: Map<String, Any>
    ): Specification<MenuItemEntity> = Specification {
        root: Root<MenuItemEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.conjunction()

        filter.forEach { (key, value) ->
            try {
                when (key) {
                    "product_name" -> {
                        if (value !is String) throw IllegalArgumentException("поле \"название продукта\" должно иметь тип String")
                        val menuItemsProductsJoin = root.join<MenuItemEntity, ProductEntity>("product")
                        val likePredicate = cb.like(menuItemsProductsJoin.get("name"), "%$value%")
                        criteria = cb.and(criteria, likePredicate)
                    }
                    "menu_item_price_min" -> {
                        val targetPrice = convertToBigDecimal(value)
                        criteria = cb.and(
                            criteria,
                            cb.greaterThanOrEqualTo(root.get<BigDecimal>("price"), targetPrice)
                        )
                    }
                    "menu_item_price_max" -> {
                        val targetPrice = convertToBigDecimal(value)
                        criteria = cb.and(
                            criteria,
                            cb.lessThanOrEqualTo(root.get<BigDecimal>("price"), targetPrice)
                        )
                    }
                    "menu_item_makes_min" -> {
                        val targetMakes = convertToShort(value)
                        criteria = cb.and(
                            criteria,
                            cb.greaterThanOrEqualTo(root.get<Short>("makes"), targetMakes)
                        )
                    }
                    "menu_item_makes_max" -> {
                        val targetMakes = convertToShort(value)
                        criteria = cb.and(
                            criteria,
                            cb.lessThanOrEqualTo(root.get<Short>("makes"), targetMakes)
                        )
                    }
                    "menu_item_topping" -> {
                        if (value !is String) throw IllegalArgumentException("Поле \"топпинг\" должно иметь тип String")
                        criteria = cb.and(
                            criteria,
                            cb.like(root.get("topping"), "%$value%")
                        )
                    }
                }
            } catch (ex: IllegalArgumentException) {
                logger.error(ex)
            }
        }

        criteria
    }

    fun menuItemCategoryFilter(
        categories: List<String>
    ): Specification<MenuItemEntity> = Specification {
        root: Root<MenuItemEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.disjunction()

        categories.forEach {
            category ->

            val categorySubquery = cq!!.subquery(Short::class.java)
            val categoryRoot = categorySubquery.from(CategoryEntity::class.java)
            categorySubquery
                .select(categoryRoot.get<Short>("id"))
                .where(cb.equal(categoryRoot.get<String>("name"), category))

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

        criteria
    }


    private fun convertToBigDecimal(value: Any): BigDecimal {
        return when (value) {
            is String -> BigDecimal(value)
            is BigDecimal -> value
            else -> throw IllegalArgumentException("Цена должна быть задана либо как строка, либо как BigDecimal")
        }
    }

    private fun convertToShort(value: Any): Short {
        return when (value) {
            is String -> value.toShort()
            is Short -> value
            else -> throw IllegalArgumentException("Объем порции должен быть задана либо как строка, либо как Short")
        }
    }
}