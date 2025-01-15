package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UnitDto (
    val id: Short?,
    @get:NotBlank
    @get:Size(min = 4, max = 32)
    val name: String,

    @get:NotBlank
    @get:Size(min = 1, max = 6)
    val label: String
)