package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import ru.manannikov.bootcupsbackend.dto.EmployeeDto
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.mappers.EmployeeMapper
import ru.manannikov.bootcupsbackend.services.DictionaryService
import java.util.*

abstract class EmployeeMapperDecorator : EmployeeMapper {
    @set:Autowired
    open lateinit var delegate: EmployeeMapper

    @set:Qualifier("roleService")
    @set:Autowired
    lateinit var roleService: DictionaryService<RoleEntity>
    @set:Autowired
    lateinit var messageSource: MessageSource

    override fun toDto(employee: EmployeeEntity): EmployeeDto = delegate.toDto(employee).apply {
        roleString = messageSource.getMessage(employee.role.name, null, Locale.getDefault())
    }

    override fun toEntity(employee: EmployeeDto): EmployeeEntity = delegate.toEntity(employee).apply {
        role = roleService.findByKey(employee.roleString!!)
    }
}