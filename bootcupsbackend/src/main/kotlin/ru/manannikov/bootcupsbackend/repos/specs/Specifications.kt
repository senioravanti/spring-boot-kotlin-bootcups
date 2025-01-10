package ru.manannikov.bootcupsbackend.repos.specs

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.apache.logging.log4j.LogManager
import org.springframework.data.jpa.domain.Specification
import ru.manannikov.bootcupsbackend.entities.CategoryEntity
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.entities.ProductEntity
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
                    "product_name" -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("название товара"))
                        val menuItemsProductsJoin = root.join<MenuItemEntity, ProductEntity>("product")
                        val likePredicate = cb.like(menuItemsProductsJoin.get("name"), "%$value%")
                        criteria = cb.and(criteria, likePredicate)
                    }
                    "menu_item_price_min" -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("минимальная цена товара"))
                        criteria = cb.and(
                            criteria,
                            cb.greaterThanOrEqualTo(root.get<BigDecimal>("price"), BigDecimal(value))
                        )
                    }
                    "menu_item_price_max" -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("максимальная цена товара"))
                        criteria = cb.and(
                            criteria,
                            cb.lessThanOrEqualTo(root.get<BigDecimal>("price"), BigDecimal(value))
                        )
                    }
                    "menu_item_makes_min" -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("минимальный объем порции"))
                        criteria = cb.and(
                            criteria,
                            cb.greaterThanOrEqualTo(root.get<Short>("makes"), value.toShort())
                        )
                    }
                    "menu_item_makes_max" -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("максимальный объем порции"))
                        criteria = cb.and(
                            criteria,
                            cb.lessThanOrEqualTo(root.get<Short>("makes"), value.toShort())
                        )
                    }
                    "menu_item_topping" -> {
                        if (value !is String) throw IllegalArgumentException(createErrorMessage("топпинг"))
                        criteria = cb.and(
                            criteria,
                            cb.like(root.get("topping"), "%$value%")
                        )
                    }
                    "categories" -> {
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

        return criteria
    }

    private fun createErrorMessage(fieldName: String, typeName: String = "String"): String = "Поле \"$fieldName\" должно иметь тип $typeName"
}