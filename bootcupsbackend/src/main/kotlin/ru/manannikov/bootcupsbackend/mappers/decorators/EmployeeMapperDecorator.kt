package ru.manannikov.bootcupsbackend.mappers.decorators

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import ru.manannikov.bootcupsbackend.dto.EmployeeRequest
import ru.manannikov.bootcupsbackend.dto.EmployeeResponse
import ru.manannikov.bootcupsbackend.entities.EmployeeEntity
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.mappers.EmployeeMapper
import ru.manannikov.bootcupsbackend.services.DictionaryService
import ru.manannikov.bootcupsbackend.utils.MiscellaneousMapper

abstract class EmployeeMapperDecorator : EmployeeMapper {
    @set:Autowired
    open lateinit var delegate: EmployeeMapper

    @set:Qualifier("roleService")
    @set:Autowired
    lateinit var roleService: DictionaryService<RoleEntity>

    @set:Autowired
    lateinit var miscellaneousMapper: MiscellaneousMapper

    override fun toDto(employee: EmployeeEntity): EmployeeResponse = delegate.toDto(employee).apply {
        role = miscellaneousMapper.dictionaryToDto(employee.role)
    }

    override fun toEntity(employee: EmployeeRequest): EmployeeEntity = delegate.toEntity(employee).apply {
        role = roleService.findByKey(employee.roleKey)
    }
}