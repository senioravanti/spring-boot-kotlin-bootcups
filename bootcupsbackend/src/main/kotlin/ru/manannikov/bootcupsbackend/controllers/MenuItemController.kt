package ru.manannikov.bootcupsbackend.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.util.MultiValueMap
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.manannikov.bootcupsbackend.dto.MenuItemDto
import ru.manannikov.bootcupsbackend.services.MenuItemService

@Validated
@RestController
@RequestMapping("v1/menu")
class MenuItemController(
    private val menuItemService: MenuItemService
) {

    @GetMapping(path = ["", "/"])
    fun all(
        @RequestParam params: MultiValueMap<String, String>
    ): List<MenuItemDto> {
        logger.info("Получен запрос со следующими параметрами: {}", params)
//        val menuItems = menuItemService.findAll()
        return listOf()
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemController::class.java)
    }
}