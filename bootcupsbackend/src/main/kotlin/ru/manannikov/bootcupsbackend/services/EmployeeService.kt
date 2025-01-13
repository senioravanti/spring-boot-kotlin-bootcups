package ru.manannikov.bootcupsbackend.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.enums.RoleEnum

interface EmployeeService {
    fun findById(id: Int): EmployeeEntity
    fun findAll(
        pageRequest: Pageable,
        roleName: RoleEnum? = null
    ): Page<EmployeeEntity>

    fun save(employeeEntity: EmployeeEntity)
    fun update(id: Int, employeeEntity: EmployeeEntity)

    fun deleteById(id: Int)

    /**
     * role_name -> название должности сотрудника
     */
    companion object {
        const val ROLE_NAME = "role_name"
    }
}
