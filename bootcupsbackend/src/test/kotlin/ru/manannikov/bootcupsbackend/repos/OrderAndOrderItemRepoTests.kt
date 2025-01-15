package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import ru.manannikov.bootcupsbackend.mappers.OrderItemMapper
import ru.manannikov.bootcupsbackend.mappers.OrderMapper

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
        val order = orderRepo.findByStatus(OrderStatus.RAISED).first()
        val orderItemCount = orderItemRepo.countByOrderId(order.id!!)
        val orderItems = orderItemRepo.findByOrderId(order.id!!)

        logger.info("---\norder:\n{}\norder item count:\n{}\n*", order, orderItemCount)
        logger.info("orderDto:\n{}\n*", orderMapper.toDto(order))

        val logMessage = StringBuilder("\n***\norderItems:\n")
        orderItems.forEach { logMessage.append(it).append("\n") }
        logMessage.append("***\nOrderItemsDto")
        orderItems.forEach {
            logMessage.append(orderItemMapper.toDto(it)).append("\n")
        }
        logger.info(logMessage.append("\n---"))

        assertEquals(1, orderItemCount)
    }

    companion object {
        private val logger = LogManager.getLogger(OrderAndOrderItemRepoTests::class.java)
    }
}