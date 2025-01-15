package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import ru.manannikov.bootcupsbackend.mappers.OrderItemMapper
import ru.manannikov.bootcupsbackend.mappers.OrderMapper
import ru.manannikov.bootcupsbackend.repos.specs.Specifications
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MAX
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_STATUS
import ru.manannikov.bootcupsbackend.utils.*
import java.math.BigDecimal

@SpringBootTest
class OrderAndOrderItemRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var orderRepo: OrderRepo
    @Autowired
    lateinit var orderItemRepo: OrderItemRepo

    @Autowired
    lateinit var orderMapper: OrderMapper
    @Autowired
    lateinit var orderItemMapper: OrderItemMapper

    @Test
    fun testCountByOrderId() {
        val order = orderRepo.findAll(
            Specifications.orderFilter(
                mapOf(ORDER_STATUS to OrderStatus.RAISED.fieldKey)
            )
        ).first()
        val orderItemCount = orderItemRepo.countByOrderId(order.id!!)
        val orderItems = orderItemRepo.findByOrderId(order.id!!, Pageable.unpaged())

        logger.info("---\norder:\n{}\norder item count:\n{}\n*", order, orderItemCount)
        logger.info("orderDto:\n{}\n*", orderMapper.toDto(order))

        val logMessage = StringBuilder("\n***\norderItems:\n")
        orderItems.forEach { logMessage.append(it).append("\n") }
        logMessage.append("***\nOrderItemsDto:\n")
        orderItems.forEach {
            logMessage.append(orderItemMapper.toDto(it)).append("\n")
        }
        logger.info(logMessage.append("\n---"))

        assertEquals(1, orderItemCount)
    }

    private fun findOrder() = orderRepo.findAll(
        Specifications.orderFilter(
            mapOf(
                EMPLOYEE_LAST_NAME to "Мананников",
                EMPLOYEE_FIRST_NAME to "Антон",
                EMPLOYEE_MIDDLE_NAME to "Олегович",
            )
        )
    ).first()

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Rollback
    @Test
    fun testOrderItemDelete() {
        val order = findOrder()

        logger.info("order: {}", order)

        val orderItems = order.orderItems

        val orderTotalAmount = BigDecimal("465.00")
        val updatedOrderTotalAmount = orderTotalAmount.minus(orderItems.last().purchase)

        val logMessage = StringBuilder("---\norder items:\n")
        orderItems.forEach {
            logMessage.append(
                orderItemMapper.toDto(it)
            ).append("\n")
        }

        logger.info(logMessage.append("---\n"))

        orderItemRepo.deleteById(orderItems.last().id!!)
        orderRepo.updateOrderTotalAmount(order.id!!)

        val updatedOrder = findOrder()
        logger.info("order after order item deletion:\n{}", updatedOrder)

        assertAll(
            { assertEquals(orderTotalAmount, order.totalAmount) },
            { assertEquals(updatedOrderTotalAmount, updatedOrder.totalAmount) }
        )
    }

    @Transactional
    @Test
    @DisplayName("Должен возвращать заказы анонимных клиентов, отсортированных по возрастанию стоимости")
    fun testOrderOrderItemUpdate() {
        val orders = orderRepo.findAll(
            Specifications.orderFilter(
                mapOf(
                    CLIENT_NAME to " ",
                    ORDER_AMOUNT_MAX to "1847.99"
                )
            ),
            PageRequest.ofSize(10).withSort(Sort.by(
                Sort.Direction.ASC, "totalAmount", "discountAmount"
            ))
        )

        val logMessage = StringBuilder("---\norders:\n")
        orders.content.forEach { logMessage.append(orderMapper.toDto(it)).append("\n") }
        logger.info(logMessage.append("totalElements: ").append(orders.totalElements).append("\ntotalPages: ").append(orders.totalPages).append("\n---"))

        assertEquals(3, orders.totalElements)


    }

    @Rollback
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    fun testFindByOrderId() {
        val order = orderRepo.findAll(
            Specifications.orderFilter(
                mapOf(
                    EMPLOYEE_EMAIL to "lurks@gmail.com"
                )
            )
        ).last()
        val orderId = order.id!!
        logger.info("origin order: {}", order)

        val orderItemsPage = orderItemRepo.findByOrderId(
            order.id!!, PageRequest.of(0, 2)
        )
        logger.info(listEntities(orderItemsPage.content, "order items"))
        logger.info("totalElements: {}; totalPages: {}", orderItemsPage.totalElements, orderItemsPage.totalPages)

        val newQuantity = 10

        var orderItem = orderItemsPage.content.first()
        orderItem.quantity = newQuantity.toShort()
        orderItem.purchase = orderItem.menuItem.price.multiply(BigDecimal(newQuantity))

        orderItem = orderItemRepo.save(orderItem)
        logger.info("updated order item: {}", orderItem)
        orderRepo.updateOrderTotalAmount(order.id!!)

        val updatedOrder = orderRepo.findById(orderId)
        logger.info("updated order: {}", updatedOrder)
    }

    companion object {
        private val logger = LogManager.getLogger(OrderAndOrderItemRepoTests::class.java)
    }
}