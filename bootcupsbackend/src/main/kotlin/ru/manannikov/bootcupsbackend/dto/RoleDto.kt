package ru.manannikov.bootcupsbackend.dto

import jakarta.validation.constraints.Size
import ru.manannikov.bootcupsbackend.entities.RoleEntity

data class RoleDto(
    val id: Short?,
    @Size(min = 4, max = 128, message = "{role-entity.name.size}")
    val name: String
) {
    fun toEntity() = RoleEntity().apply {
        this.id = this@RoleDto.id
        this.name = this@RoleDto.name
    }

    companion object {
        fun of(roleEntity: RoleEntity) = RoleDto(
            id = roleEntity.id,
            name = roleEntity.name
        )
    }
}