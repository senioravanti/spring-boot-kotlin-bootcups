package ru.manannikov.bootcupsbackend.repos

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity

interface EmployeeRepo
:
    ListCrudRepository<EmployeeEntity, Int>,
    ListPagingAndSortingRepository<EmployeeEntity, Int>,
    JpaSpecificationExecutor<EmployeeEntity>
{}