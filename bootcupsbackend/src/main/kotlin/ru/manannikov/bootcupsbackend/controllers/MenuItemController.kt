package ru.manannikov.bootcupsbackend.controllers

import jakarta.validation.Valid
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.manannikov.bootcupsbackend.dto.*
import ru.manannikov.bootcupsbackend.enums.MenuItemSortFields
import ru.manannikov.bootcupsbackend.mappers.MenuItemMapper
import ru.manannikov.bootcupsbackend.services.MenuItemService
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_MAKES_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MAX
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_TOPPING
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PRODUCT_NAME
import ru.manannikov.bootcupsbackend.utils.MiscellaneousMapper
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.sortFromSortCriteria

@Validated
@RestController
@RequestMapping("/v1/menu")
class MenuItemController(
    private val menuItemService: MenuItemService,

    private val menuItemMapper: MenuItemMapper,
    private val miscellaneousMapper: MiscellaneousMapper
) {

    @GetMapping(path = ["", "/"])
    fun findAll(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam params: MultiValueMap<String, String>
    ): PaginationResponse<MenuItemResponse> {
        logger.info("Получен запрос со следующими параметрами:\n{}", params)

        var pageRequest = PageRequest.of(pageNumber, pageSize)
        var filter: MutableMap<String, Any>? = null

        params.forEach { (key, value) ->
            when (key) {
                SORT -> {
                    pageRequest = pageRequest.withSort(
                        sortFromSortCriteria(
                            value,
                            MenuItemSortFields.entries.map { it.fieldKey }
                        )
                    )
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

                    logger.debug("value:\n{}", value)
                    filter!![key] = value
                }
            }
        }

        logger.info("page request: {};\nfilter: {}", pageRequest, filter)

        return miscellaneousMapper.toPaginationResponse(
            menuItemService.findAll(pageRequest, filter),
            menuItemMapper::toDto
        )
    }

    /**
     * Возвращает ключи сортировки (в нижнем регистре) и их названия
     */
    @GetMapping("/sort-fields")
    fun sortFields(): List<FieldEnumDto> = miscellaneousMapper.toFieldEnumDto(
        MenuItemSortFields.entries
    )

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): MenuItemResponse = menuItemMapper.toDto(
        menuItemService.findById(id)
    )

    @PostMapping(path = ["", "/"])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody menuItem: MenuItemRequest
    ) {
        logger.trace("Запрос на создание новой позиции меню:\n{}", menuItem)

        menuItemService.save(
            menuItemMapper.toEntity(menuItem)
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @Valid @RequestBody menuItem: MenuItemRequest
    ) {
        logger.trace("Запрос на обновление позиции меню с идентификатором {}:\n{}", id, menuItem)
        menuItemService.update(
            id,
            menuItemMapper.toEntity(menuItem)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        logger.trace("Запрос на удаление позиции меню с идентификатором: {}", id)
        menuItemService.deleteById(id)
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemController::class.java)
    }
}