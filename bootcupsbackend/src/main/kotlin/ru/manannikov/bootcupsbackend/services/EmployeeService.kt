package ru.manannikov.bootcupsbackend.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity

interface EmployeeService {
    fun findById(id: Int): EmployeeEntity
    fun findAll(
        pageRequest: Pageable,
        filter: Map<String, String>? = null
    ): Page<EmployeeEntity>

    fun save(employeeEntity: EmployeeEntity)
    fun update(id: Int, employeeEntity: EmployeeEntity)

    fun deleteById(id: Int)

    /**
     * role_name -> название должности сотрудника
     */
    companion object {
        const val LAST_NAME = "last_name"
        const val FIRST_NAME = "first_name"
        const val MIDDLE_NAME = "middle_name"

        const val ROLE_NAME = "role_name"
    }
}
