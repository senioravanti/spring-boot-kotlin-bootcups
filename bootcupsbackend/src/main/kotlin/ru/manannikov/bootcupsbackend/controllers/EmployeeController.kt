package ru.manannikov.bootcupsbackend.controllers

import jakarta.validation.Valid
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import ru.manannikov.bootcupsbackend.dto.EmployeeDto
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.enums.EmployeeSortFields
import ru.manannikov.bootcupsbackend.enums.RoleEnum
import ru.manannikov.bootcupsbackend.services.EmployeeService
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.ROLE_NAME
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.sortFromSortCriteria

@RestController
@RequestMapping("/v1/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @GetMapping(path = ["", "/"])
    fun findAll(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam(name = SORT, required = false) sortCriteria: List<String>?,
        @RequestParam(name = ROLE_NAME, required = false) roleName: String?
    ): PaginationResponse<EmployeeDto> {
        var pageRequest = PageRequest.of(pageNumber, pageSize)

        var roleNameEnum: RoleEnum? = null
        roleName ?.let {
            roleNameEnum = try {
                RoleEnum.valueOf(it)
            } catch (ex: IllegalArgumentException) {
                logger.error("Передана несуществующая должность: {}", it)
                null
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

        return PaginationResponse.of(
            employeeService.findAll(pageRequest, roleNameEnum),
            EmployeeDto::of
        )
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): EmployeeDto = EmployeeDto.of(
        employeeService.findById(id)
    )

    @PostMapping(path = ["", "/"])
    fun create(
       @Valid @RequestBody employee: EmployeeDto
    ) {
        logger.trace("Запрос на регистрацию нового сотрудника:\n{}", employee)
        employeeService.save(
            employee.toEntity()
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @Valid @RequestBody employee: EmployeeDto
    ) {
        logger.trace("Запрос на обновление данных сотрудника с идентификатором {}:\n{}", id, employee)
        employeeService.update(
            id,
            employee.toEntity()
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