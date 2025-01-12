package ru.manannikov.bootcupsbackend.dto

/**
 * Инкапсулирует ключи и названия полей сортировки или категорий пищевых продуктов
 */
data class FieldEnumDto(
    val key: String,
    val fieldName: String
)
