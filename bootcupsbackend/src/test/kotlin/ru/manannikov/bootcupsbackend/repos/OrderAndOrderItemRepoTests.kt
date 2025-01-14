package ru.manannikov.bootcupsbackend.repos

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.enums.OrderStatus
import ru.manannikov.bootcupsbackend.mappers.OrderMapper

@SpringBootTest
class OrderAndOrderItemRepoTests : TestcontainersTest() {
    @Autowired
    lateinit var orderRepo: OrderRepo
    @Autowired
    lateinit var orderItemRepo: OrderItemRepo

    @Autowired
    lateinit var orderMapper: OrderMapper

    @Test
    fun testCountByOrderId() {
        val order = orderRepo.findByStatus(OrderStatus.RAISED).first()
        val orderItemCount = orderItemRepo.countByOrderId(order.id!!)

        val orderDto = orderMapper.toDto(order)
        logger.info("---\norder:\n{}\norder item count:\n{}\n*", order, orderItemCount)
        logger.info("orderDto:\n{}\n---", orderDto)

        assertEquals(1, orderItemCount)
    }

    companion object {
        private val logger = LogManager.getLogger(OrderAndOrderItemRepoTests::class.java)
    }
}