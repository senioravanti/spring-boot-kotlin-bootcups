package ru.manannikov.bootcupsbackend.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.exceptions.EntityAlreadyExistsException
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.repos.EmployeeRepo
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import ru.manannikov.bootcupsbackend.services.EmployeeService
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.tableNameFromEntity

@Transactional(readOnly = true)
@Service("employeeService")
class EmployeeServiceImpl(
    private val employeeRepo: EmployeeRepo
)
    : EmployeeService
{
    override fun findById(id: Int): EmployeeEntity {
        if (employeeRepo.count() == 0L) throw NotFoundException(EmployeeEntity::class.java)

        return employeeRepo.findById(id).orElseThrow {
            NotFoundException(id.toString(), EmployeeEntity::class.java)
        }
    }

    override fun findAll(
        pageRequest: Pageable,
        filter: Map<String, String>?
    )
        : Page<EmployeeEntity>
    {
        logger.debug("Получены:\npageRequest: {},\nroleName: {}", pageRequest, filter)
        val employees = if (filter != null) {
            employeeRepo.findAll(
                Specifications.employeeFilter(filter),
                pageRequest
            )
        } else {
            employeeRepo.findAll(
                pageRequest
            )
        }
        logger.debug("Из таблицы employees получены следующие записи:\n{}", employees)
        return employees
    }

    @Transactional
    override fun save(employeeEntity: EmployeeEntity) {
        if (employeeEntity.id != null) throw EntityAlreadyExistsException(
            tableNameFromEntity(EmployeeEntity::class)
        )

        val savedEmployee = employeeRepo.save(employeeEntity)
        logger.debug("Сотрудник успешно зарегистрирован:\n{}", savedEmployee)
    }

    /**
     * Пароль вообще говоря надо шифровать
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun update(id: Int, employeeEntity: EmployeeEntity) {
        var existingEmployee = findById(id).apply {
            lastName = employeeEntity.lastName
            firstName = employeeEntity.firstName
            middleName = employeeEntity.middleName

            username = employeeEntity.username
            password = employeeEntity.password

            email = employeeEntity.email
            phoneNumber = employeeEntity.phoneNumber

            role = employeeEntity.role
        }
        existingEmployee = employeeRepo.save(existingEmployee)
        logger.debug("Данные сотрудника c идентификатором {} успешно обновлены:\n{}", id, existingEmployee)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteById(id: Int) {
        findById(id)
        employeeRepo.deleteById(id)
        logger.debug("Данные сотрудника с идентификатором {} успешно удалены", id)
    }

    companion object {
        private val logger = LogManager.getLogger(EmployeeServiceImpl::class.java)
    }
}