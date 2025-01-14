package ru.manannikov.bootcupsbackend.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity

interface MenuItemService : CrudService<MenuItemEntity, Int> {
    fun findAll(
        pageRequest: Pageable,
        filter: Map<String, Any>? = null,
    ): Page<MenuItemEntity>

    /**
     * Названия параметров запросов, относящихся к пагинации, фильтрации и сортировке позиций меню
     * product_name -> название искомого пищевого продукта
     *
     * menu_item_price_min -> минимальная цена позиции меню
     * menu_item_price_max -> максимальная цена позиции меню
     *
     * menu_item_makes_min -> минимальный объем порции позиции меню
     * menu_item_makes_max -> максимальный объем порции позиции меню
     *
     * menu_item_topping -> особенности пищевого продукта, например посыпка, начинка и т.п.
     *
     * category -> Массив категорий
     *      compote, healthy_nutrition, sourdough_bread
     *
     */
    companion object {
        const val PRODUCT_NAME = "product_name"

        const val MENU_ITEM_PRICE_MIN = "menu_item_price_min"
        const val MENU_ITEM_PRICE_MAX = "menu_item_price_max"

        const val MENU_ITEM_MAKES_MIN = "menu_item_makes_min"
        const val MENU_ITEM_MAKES_MAX = "menu_item_makes_max"

        const val MENU_ITEM_TOPPING = "menu_item_topping"

        const val CATEGORY = "category"
    }
}