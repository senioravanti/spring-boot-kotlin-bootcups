package ru.manannikov.bootcupsbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.manannikov.bootcupsbackend.dto.SortFilterFieldDto
import ru.manannikov.bootcupsbackend.enums.CategoryEnum
import ru.manannikov.bootcupsbackend.utils.MessageUtils

@RestController
@RequestMapping("/v1/category")
class CategoryController(
    private val messageUtils: MessageUtils
) {
    /**
     * Возвращает ключи категорий пищевых продуктов (в нижнем регистре) и их описания
     */
    @GetMapping(path = ["", "/"])
    fun all(): List<SortFilterFieldDto> {
        return messageUtils.mapFieldEnumToEnumDto(
            CategoryEnum.entries
        )
    }
}