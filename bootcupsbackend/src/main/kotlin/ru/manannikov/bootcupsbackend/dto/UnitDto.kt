package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.UnitEntity

data class UnitDto (
    val id: Short?,
    @field:NotBlank
    @field:Size(min = 4, max = 32)
    val name: String,

    @field:NotBlank
    @field:Size(min = 1, max = 6)
    val label: String
) {
    fun toEntity() = UnitEntity().apply {
        this.id = this@UnitDto.id
        this.name = this@UnitDto.name
    }

    companion object {
        fun of(unitEntity: UnitEntity) = UnitDto(
            unitEntity.id,
            unitEntity.name,
            unitEntity.label
        )
    }
}