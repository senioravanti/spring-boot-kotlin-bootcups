package ru.manannikov.bootcupsbackend.repos.specs

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity
import ru.manannikov.bootcupsbackend.entities.ProductEntity
import java.math.BigDecimal

object Specifications {
    fun menuItemPriceCriteria(
        minMenuItemPrice: BigDecimal? = null,
        maxMenuItemPrice: BigDecimal? = null,
        productName: String? = null
    ): Specification<MenuItemEntity> = Specification {
        root: Root<MenuItemEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.conjunction()

        productName ?.let {
            val menuItemsProductsJoin = root.join<MenuItemEntity, ProductEntity>("product")
            val likePredicate = cb.like(menuItemsProductsJoin.get("name"), "%$productName%")
            criteria = cb.and(criteria, likePredicate)
        }

        val menuItemPrice = root.get<BigDecimal>("price")
        minMenuItemPrice ?.let {
            val greaterThanPredicate = cb.greaterThanOrEqualTo(menuItemPrice, minMenuItemPrice)
            criteria = cb.and(criteria, greaterThanPredicate)
        }

        maxMenuItemPrice ?.let {
            val lessThanPredicate = cb.lessThanOrEqualTo(menuItemPrice, maxMenuItemPrice)
            criteria = cb.and(criteria, lessThanPredicate)
        }

        criteria
    }

    fun menuItemProductNameCriteria(
        productName: String? = null
    ): Specification<MenuItemEntity> = Specification {
        root: Root<MenuItemEntity>, cq: CriteriaQuery<*>?, cb: CriteriaBuilder ->
        var criteria = cb.conjunction()

        val menuItemsProductsJoin = root.join<MenuItemEntity, ProductEntity>("product")
        productName ?.let {
            val likePredicate = cb.like(menuItemsProductsJoin.get("name"), "%$productName%")
            criteria = cb.and(criteria, likePredicate)
        }

        criteria
    }
}