package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity

interface MenuItemRepo
:
    ListCrudRepository<MenuItemEntity, Int>,
    ListPagingAndSortingRepository<MenuItemEntity, Int>,
    JpaSpecificationExecutor<MenuItemEntity>
{
    @Query("SELECT m FROM MenuItemEntity m WHERE m.product.id IN (SELECT p.id FROM ProductEntity p JOIN p.categories pc WHERE pc.id = (SELECT c.id FROM CategoryEntity c WHERE c.name = :cn))")
    fun findAllByCategory(
        @Param("cn") category: String
    ): List<MenuItemEntity>
}