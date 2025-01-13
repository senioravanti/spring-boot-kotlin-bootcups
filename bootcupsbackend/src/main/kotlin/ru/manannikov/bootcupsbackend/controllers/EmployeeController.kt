package ru.manannikov.bootcupsbackend.controllers

import jakarta.validation.Valid
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.manannikov.bootcupsbackend.dto.EmployeeDto
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.enums.EmployeeSortFields
import ru.manannikov.bootcupsbackend.enums.FieldEnum
import ru.manannikov.bootcupsbackend.exceptions.NotFoundException
import ru.manannikov.bootcupsbackend.services.DictionaryService
import ru.manannikov.bootcupsbackend.services.EmployeeService
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.ROLE_NAME
import ru.manannikov.bootcupsbackend.utils.ModelConverter
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
    private val modelConverter: ModelConverter
) {
    @GetMapping(path = ["", "/"])
    fun findAll(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam(name = SORT, required = false) sortCriteria: List<String>?,
        @RequestParam filter: Map<String, String>?
    ): PaginationResponse<EmployeeDto> {
        var pageRequest = PageRequest.of(pageNumber, pageSize)

        logger.debug("filter: {}", filter)
        var actualFilter: MutableMap<String, String>? = null
        filter ?.let {
            actualFilter = it.toMutableMap()
            actualFilter!![ROLE_NAME] ?. let {
                roleKey ->
                try {
                    roleService.findByKey(roleKey)
                } catch (ex: NotFoundException) {
                    logger.error("Передана несуществующая должность: {}", it)
                    actualFilter!!.remove(ROLE_NAME)
                }
                actualFilter!!.remove(PAGE_NUMBER)
                actualFilter!!.remove(PAGE_SIZE)
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

        return modelConverter.toPaginationResponse(
            employeeService.findAll(pageRequest, actualFilter),
            modelConverter::employeeToDto
        )
    }

    @GetMapping("/sort-fields")
    fun sortFields(): List<FieldEnum> = EmployeeSortFields.entries

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): EmployeeDto = modelConverter.employeeToDto(
        employeeService.findById(id)
    )

    @PostMapping(path = ["", "/"])
    fun create(
       @Valid @RequestBody employee: EmployeeDto
    ) {
        logger.trace("Запрос на регистрацию нового сотрудника:\n{}", employee)
        employeeService.save(
            modelConverter.employeeToEntity(employee)
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
            modelConverter.employeeToEntity(employee)
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