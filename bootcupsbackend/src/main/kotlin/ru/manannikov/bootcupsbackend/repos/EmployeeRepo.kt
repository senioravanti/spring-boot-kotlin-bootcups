package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.query.Param
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity

interface EmployeeRepo : ListCrudRepository<EmployeeEntity, Int>, ListPagingAndSortingRepository<EmployeeEntity, Int> {
    /**
     * Название роли должно быть в верхнем регистре
     */
    @Query("SELECT e FROM EmployeeEntity e WHERE e.role.name = :rn")
    fun findByRoleName(@Param("rn") roleName: String)
}