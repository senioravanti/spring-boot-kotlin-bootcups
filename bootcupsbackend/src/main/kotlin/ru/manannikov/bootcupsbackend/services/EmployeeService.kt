package ru.manannikov.bootcupsbackend.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity

interface EmployeeService : CrudService<EmployeeEntity, Int> {
    fun findAll(
        pageRequest: Pageable,
        filter: Map<String, String>? = null
    ): Page<EmployeeEntity>

    /**
     * role_name -> название должности сотрудника
     */
    companion object {
        const val EMPLOYEE_ROLE_NAME = "role_name"
    }
}
