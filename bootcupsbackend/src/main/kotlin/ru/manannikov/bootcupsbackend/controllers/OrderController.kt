package ru.manannikov.bootcupsbackend.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import ru.manannikov.bootcupsbackend.controllers.EmployeeController.Companion
import ru.manannikov.bootcupsbackend.dto.FieldEnumDto
import ru.manannikov.bootcupsbackend.dto.OrderResponse
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.entities.OrderEntity
import ru.manannikov.bootcupsbackend.enums.OrderSortFields
import ru.manannikov.bootcupsbackend.mappers.OrderItemMapper
import ru.manannikov.bootcupsbackend.mappers.OrderMapper
import ru.manannikov.bootcupsbackend.services.OrderItemService
import ru.manannikov.bootcupsbackend.services.OrderService
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MAX
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MIN
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_CREATED_AFTER
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_CREATED_BEFORE
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_STATUS
import ru.manannikov.bootcupsbackend.utils.*
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.sortFromSortCriteria

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderService: OrderService,
    private val orderItemService: OrderItemService,

    private val orderMapper: OrderMapper,
    private val orderItemMapper: OrderItemMapper,
    private val miscellaneousMapper: MiscellaneousMapper
) {
    @GetMapping(path = ["", "/"])
    fun findAll(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam(name = SORT, required = false) sortCriteria: List<String>?,
        @RequestParam params: Map<String, String>
    ): PaginationResponse<OrderResponse> {
        var pageRequest = PageRequest.of(pageNumber, pageSize)

        logger.debug("sort criteria: {}", sortCriteria)
        sortCriteria ?.let {
            pageRequest = pageRequest.withSort(
                sortFromSortCriteria(
                    it,
                    OrderSortFields.entries.map { it.fieldKey }
                )
            )
        }


        logger.debug("filter: {}", params)
        val filter: MutableMap<String, String> = mutableMapOf()
        params.forEach { (key, value) ->
            when (key) {
                ORDER_STATUS -> filter[key] = value
                ORDER_AMOUNT_MAX, ORDER_AMOUNT_MIN -> {
                    filter[key] = if (key.contains(",")) {
                        logger.debug("Вещественное число содержит запятую")
                        value.replace(",", ".")
                    } else {
                        value
                    }
                }
                ORDER_CREATED_BEFORE, ORDER_CREATED_AFTER -> {
                    val unixTimeRegex = Regex(UNIXTIME_REXEP)
                    if (!unixTimeRegex.matches(value)) throw IllegalArgumentException("exception.illegal-argument.order.filter.created")

                    filter[key] = value
                }
                EMPLOYEE_LAST_NAME, EMPLOYEE_FIRST_NAME, EMPLOYEE_MIDDLE_NAME, CLIENT_EMAIL -> {
                    if (key.isNotBlank()) {
                        filter[key] = value
                    } else {
                        logger.debug("Заданы пустые адрес электронной почты, фамилия, имя или отчество сотрудника, или пустой адрес электронной почты клиента")
                    }
                }
            }

        }

        return miscellaneousMapper.toPaginationResponse(
            orderService.findAll(pageRequest, filter),
            orderMapper::toDto
        )
    }

    /**
     * Возвращает ключи сортировки (в нижнем регистре) и их названия
     */
    @GetMapping("/sort-fields")
    fun sortFields(): List<FieldEnumDto> = miscellaneousMapper.toFieldEnumDto(
        OrderSortFields.entries
    )

    /**
     * Должен возвращать список OrderItemDto
     */
    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ) {}

    companion object {
        private val logger = LogManager.getLogger(OrderController::class.java)
    }
}