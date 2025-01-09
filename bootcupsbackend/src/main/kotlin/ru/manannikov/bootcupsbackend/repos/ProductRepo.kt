package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import ru.manannikov.bootcupsbackend.entities.ProductEntity

interface ProductRepo : JpaRepository<ProductEntity, Short> {
    fun findByName(name: String): ProductEntity
}