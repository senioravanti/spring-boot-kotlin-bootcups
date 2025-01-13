package ru.manannikov.bootcupsbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.manannikov.bootcupsbackend.dto.FieldEnumDto
import ru.manannikov.bootcupsbackend.enums.CategoryEnum
import ru.manannikov.bootcupsbackend.utils.ModelConverter

@RestController
@RequestMapping("/v1/category")
class CategoryController(
    private val modelConverter: ModelConverter
) {
    /**
     * Возвращает ключи категорий пищевых продуктов (в нижнем регистре) и их описания
     */
    @GetMapping(path = ["", "/"])
    fun findAll(): List<FieldEnumDto> {
        return modelConverter.fieldEnumToFieldEnumDto(
            CategoryEnum.entries
        )
    }
}