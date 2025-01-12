package ru.manannikov.bootcupsbackend.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.util.MultiValueMap
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.manannikov.bootcupsbackend.dto.MenuItemDto
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.dto.SortFilterFieldDto
import ru.manannikov.bootcupsbackend.enums.MenuItemSortFields
import ru.manannikov.bootcupsbackend.services.MenuItemService
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_TOPPING
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PAGE_SIZE
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PRODUCT_NAME
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.SORT
import ru.manannikov.bootcupsbackend.utils.MessageUtils

@Validated
@RestController
@RequestMapping("/v1/menu")
class MenuItemController(
    private val menuItemService: MenuItemService,
    private val messageUtils: MessageUtils
) {

    @GetMapping(path = ["", "/"])
    fun all(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam params: MultiValueMap<String, String>
    ): PaginationResponse<MenuItemDto> {
        logger.info("Получен запрос со следующими параметрами:\n{}", params)

        var pageRequest = PageRequest.of(pageNumber, pageSize)
        var filter: MutableMap<String, Any>? = null

        var sortOrders: List<Order>?

        params.forEach { (key, value) ->
            when (key) {
                SORT -> {
                    val sortDirectionRegex = Regex("asc|desc")
                    sortOrders = value.map {
                        val sortData = it.split("_")

                        if (sortData.size != 2 || !sortDirectionRegex.matches(sortData[1]))
                            throw IllegalArgumentException("Заданы некорректные критерии сортировки")

                        Order(
                            Sort.Direction.fromString(sortData[1]),
                            sortData[0]
                        )
                    }
                    pageRequest = pageRequest.withSort(Sort.by(sortOrders!!))
                }
                PRODUCT_NAME,
                MENU_ITEM_PRICE_MIN, MENU_ITEM_PRICE_MAX,
                MENU_ITEM_MAKES_MIN, MENU_ITEM_MAKES_MAX,
                MENU_ITEM_TOPPING -> {
                    if (filter == null)
                        filter = mutableMapOf()

                    filter!![key] = value.first()
                }
                CATEGORY -> {
                    if (filter == null)
                        filter = mutableMapOf()

                    filter!![key] = value.map { it.uppercase() }
                }
            }
        }

        logger.info("page request: {};\nfilter: {}", pageRequest, filter)

        val menuItemPage = menuItemService.findAll(pageRequest, filter)
        val content = menuItemPage.content.map { MenuItemDto.of(it) }
        return PaginationResponse(
            content = content,

            currentPageNumber = menuItemPage.number,
            totalElements = menuItemPage.totalElements,
            totalPages = menuItemPage.totalPages,

            hasPrevious = menuItemPage.hasPrevious(),
            hasNext = menuItemPage.hasNext()
        )
    }

    /**
     * Возвращает ключи сортировки (в нижнем регистре) и их названия
     */
    @GetMapping(path = ["/sort-fields"])
    fun sortFields(): List<SortFilterFieldDto> {
        return messageUtils.mapFieldEnumToEnumDto(
            MenuItemSortFields.entries
        )
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemController::class.java)
    }
}