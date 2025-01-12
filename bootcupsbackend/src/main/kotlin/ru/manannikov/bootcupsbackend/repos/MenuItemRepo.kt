package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import ru.manannikov.bootcupsbackend.entities.MenuItemEntity

interface MenuItemRepo
:
    ListCrudRepository<MenuItemEntity, Int>,
    ListPagingAndSortingRepository<MenuItemEntity, Int>,
    JpaSpecificationExecutor<MenuItemEntity>
{}