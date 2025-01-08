package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity

interface MenuItemRepository : JpaRepository<MenuItemEntity, Int>, JpaSpecificationExecutor<MenuItemEntity> {
//    @Query("SELECT m FROM MenuItemEntity m WHERE m.product.name = :pn")
//    fun findAllByProductName(@Param("pn") productName: String, sort: Sort): List<MenuItemEntity>
}