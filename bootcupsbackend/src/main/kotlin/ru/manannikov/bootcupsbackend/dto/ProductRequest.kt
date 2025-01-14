package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class ProductRequest(
    var id: Short?,

    @get:NotBlank(message = "{product-entity.name.not-blank}")
    @get:Size(min = 3, max = 512, message = "{product-entity.name.size}")
    val name: String,

    val description: String?,

    val categoryKeys: Set<String>
)
