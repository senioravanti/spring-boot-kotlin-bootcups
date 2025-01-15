package ru.manannikov.bootcupsbackend.controllers

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.apache.logging.log4j.LogManager
import org.hibernate.validator.constraints.Range
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.manannikov.bootcupsbackend.dto.*
import ru.manannikov.bootcupsbackend.enums.OrderItemSortFields
import ru.manannikov.bootcupsbackend.enums.OrderSortFields
import ru.manannikov.bootcupsbackend.mappers.OrderItemMapper
import ru.manannikov.bootcupsbackend.mappers.OrderMapper
import ru.manannikov.bootcupsbackend.services.EmployeeService
import ru.manannikov.bootcupsbackend.services.OrderItemService
import ru.manannikov.bootcupsbackend.services.OrderService
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MAX
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MIN
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_CREATED_AFTER
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_CREATED_BEFORE
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_STATUS
import ru.manannikov.bootcupsbackend.utils.*
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.sortCriteriaToPageRequest
import ru.manannikov.bootcupsbackend.utils.ServiceUtils.stringToOrderStatus

@Validated
@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderService: OrderService,
    private val orderItemService: OrderItemService,
    private val employeeService: EmployeeService,

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
            pageRequest = sortCriteriaToPageRequest(
                it, pageRequest, OrderSortFields.entries
            )
        }

        logger.debug("filter: {}", params)
        val filter: MutableMap<String, String> = mutableMapOf()
        params.forEach { (key, value) ->
            when (key) {
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
                ORDER_STATUS, EMPLOYEE_LAST_NAME, EMPLOYEE_FIRST_NAME, EMPLOYEE_MIDDLE_NAME, CLIENT_EMAIL -> {
                    if (key.isNotBlank()) {
                        filter[key] = value
                    } else {
                        logger.warn("Задано пустое значение одного из фильтров:\nkey: {}, value: {}", key, value)
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
    fun orderSortFields(): List<FieldEnumDto> = miscellaneousMapper.toFieldEnumDto(
        OrderSortFields.entries
    )
    @GetMapping("/items/sort-fields")
    fun orderItemSortFields(): List<FieldEnumDto> = miscellaneousMapper.toFieldEnumDto(
        OrderItemSortFields.entries
    )
    /**
     * Должен возвращать список OrderItemDto
     */
    @GetMapping("/{id}")
    fun findById(
        @RequestParam(name = PAGE_NUMBER) pageNumber: Int,
        @RequestParam(name = PAGE_SIZE) pageSize: Int,

        @RequestParam(name = SORT, required = false) sortCriteria: List<String>?,

        @PathVariable("id") orderId: Int,
    ): PaginationResponse<OrderItemResponse>? {
        orderService.findById(orderId)

        var pageRequest = PageRequest.of(pageNumber, pageSize)
        sortCriteria ?.let {
            pageRequest = sortCriteriaToPageRequest(
                it, pageRequest, OrderItemSortFields.entries
            )
        }

        return miscellaneousMapper.toPaginationResponse(
            orderItemService.findByOrderId(
                orderId, pageRequest
            ),
            orderItemMapper::toDto
        )
    }

    @PostMapping(path = ["", "/"])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody order: OrderRequest,
        @Valid @RequestBody orderItems: List<OrderItemRequest>
    ) {
        logger.trace("Запрос на создание нового заказа:\norder: {}\norder items: {}", order, orderItems)

        val createdOrder = orderService.save(
            orderMapper.toEntity(order)
        )
        orderItems.forEach {
            orderItemService.save(
                createdOrder, orderItemMapper.toEntity(it)
            )
        }
    }

    /**
     * Обновить или удалить сам заказ. В случае удаления заказа будут удалены также все пункты заказа
     */
    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,

        @RequestParam("employee_id") employeeId: Int,
        @RequestParam("status") @NotBlank statusString: String
    ) {
        logger.trace("Запрос на обновление заказа с идентификатором {}:\nemployee request: {}\nstatus: {}", id, employeeId, statusString)
        orderService.update(
            id, employeeService.findById(employeeId), stringToOrderStatus(statusString)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        logger.trace("Запрос на удаление заказа с идентификатором {}", id)
        orderService.deleteById(id)
    }

    /**
     * Обновить или удалить пункт заказа
     */
    @PutMapping("/item/{id}")
    fun updateItem(
        @PathVariable("id") id: Long,
        @RequestParam("quantity") @Range(min = 1, max = 50) quantity: Short
    ) {
        logger.trace("Запрос на обновление пункта заказа с идентификатором {}:\nquantity: {}", id, quantity)
        orderItemService.update(id, quantity)
    }

    @DeleteMapping("/item/{id}")
    fun deleteItem(
        @PathVariable("id") id: Long
    ) {
        logger.trace("Запрос на удаление пункта заказа с идентификатором {}", id)
        orderItemService.deleteById(id)
    }

    companion object {
        private val logger = LogManager.getLogger(OrderController::class.java)
    }
}