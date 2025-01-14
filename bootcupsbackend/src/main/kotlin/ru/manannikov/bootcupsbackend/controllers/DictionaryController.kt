package ru.manannikov.bootcupsbackend.controllers

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.manannikov.bootcupsbackend.dto.DictionaryDto
import ru.manannikov.bootcupsbackend.entities.CategoryEntity
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.services.DictionaryService
import ru.manannikov.bootcupsbackend.utils.MiscellaneousMapper

@RestController
@RequestMapping("/v1/dictionary")
class DictionaryController(
    @Qualifier("roleService")
    private val roleService: DictionaryService<RoleEntity>,
    @Qualifier("categoryService")
    private val categoryService: DictionaryService<CategoryEntity>,

    private val modelConverter: MiscellaneousMapper
) {
    @GetMapping("/roles")
    fun findAllRoles(): List<DictionaryDto> = modelConverter.dictionaryToDto(
        roleService.findAll()
    )

    @GetMapping("/categories")
    fun findAllCategories(): List<DictionaryDto> = modelConverter.dictionaryToDto(
        categoryService.findAll()
    )

}