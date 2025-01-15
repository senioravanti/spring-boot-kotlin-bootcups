package ru.manannikov.bootcupsbackend.controllers

import jakarta.validation.Valid
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.manannikov.bootcupsbackend.dto.EmployeeRequest
import ru.manannikov.bootcupsbackend.dto.EmployeeResponse
import ru.manannikov.bootcupsbackend.dto.FieldEnumDto
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.enums.EmployeeSortFields
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.mappers.EmployeeMapper
import ru.manannikov.bootcupsbackend.services.DictionaryService
import ru.manannikov.bootcupsbackend.services.EmployeeService
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.FIRST_NAME
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.LAST_NAME
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.MIDDLE_NAME
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.EMPLOYEE_ROLE_NAME
import ru.manannikov.bootcupsbackend.utils.MiscellaneousMapper
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.sortFromSortCriteria

@Validated
@RestController
@RequestMapping("/v1/employee")
class EmployeeController(
    private val employeeService: EmployeeService,
    @Qualifier("roleService")
    private val roleService: DictionaryService<RoleEntity>,

    private val employeeMapper: EmployeeMapper,
    private val miscellaneousMapper: MiscellaneousMapper
) {
    @GetMapping(path = ["", "/"])
    fun findAll(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam(name = SORT, required = false) sortCriteria: List<String>?,
        @RequestParam params: Map<String, String>
    ): PaginationResponse<EmployeeResponse> {
        var pageRequest = PageRequest.of(pageNumber, pageSize)

        logger.debug("filter: {}", params)

        val filter: MutableMap<String, String> = mutableMapOf()
        params.forEach { (key, value) ->
            when (key) {
                LAST_NAME, FIRST_NAME, MIDDLE_NAME -> {
                    if (key.isNotBlank())
                        filter[key] = value
                }
                EMPLOYEE_ROLE_NAME -> {
                    try {
                        roleService.findByKey(key)
                        filter[key] = value
                    } catch (ex: NotFoundException) {
                        logger.error("Передана несуществующая должность: {}", key)
                    }
                }
            }
        }

        sortCriteria ?.let {
            pageRequest = pageRequest.withSort(
                sortFromSortCriteria(
                    it,
                    EmployeeSortFields.entries.map { it.fieldKey }
                )
            )
        }

        return miscellaneousMapper.toPaginationResponse(
            employeeService.findAll(pageRequest, filter),
            employeeMapper::toDto
        )
    }

    @GetMapping("/sort-fields")
    fun sortFields(): List<FieldEnumDto> = miscellaneousMapper.toFieldEnumDto(
        EmployeeSortFields.entries
    )

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): EmployeeResponse = employeeMapper.toDto(
        employeeService.findById(id)
    )

    @PostMapping(path = ["", "/"])
    fun create(
       @Valid @RequestBody employee: EmployeeRequest
    ) {
        logger.trace("Запрос на регистрацию нового сотрудника:\n{}", employee)
        employeeService.save(
            employeeMapper.toEntity(employee)
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @Valid @RequestBody employee: EmployeeRequest
    ) {
        logger.trace("Запрос на обновление данных сотрудника с идентификатором {}:\n{}", id, employee)
        employeeService.update(
            id,
            employeeMapper.toEntity(employee)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        logger.trace("Запрос на удаление данных сотрудника с идентификатором {} ...", id)
        employeeService.deleteById(id)
    }

    companion object {
        private val logger = LogManager.getLogger(EmployeeController::class.java)
    }
}