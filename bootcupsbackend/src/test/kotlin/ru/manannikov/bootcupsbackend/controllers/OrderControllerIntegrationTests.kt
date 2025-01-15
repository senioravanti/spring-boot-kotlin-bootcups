package ru.manannikov.bootcupsbackend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MAX
import ru.manannikov.bootcupsbackend.services.OrderService.Companion.ORDER_AMOUNT_MIN
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT

@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG)
@SpringBootTest
class OrderControllerIntegrationTests : TestcontainersTest() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var objectWriter: ObjectWriter

    @BeforeEach
    fun setUp() {
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter()
    }

    @Test
    @DisplayName("Должен выводить заказы общей стоимостью не более 3000")
    fun testFindAll() {
        mockMvc
            .get("/api/v1/orders/") {
                contextPath = "/api"

                param(PAGE_SIZE, "4")
                param(PAGE_NUMBER, "0")

                param(ORDER_AMOUNT_MAX, "3000.00")
                param(ORDER_AMOUNT_MIN, "1000.00")

                param(SORT, "total_amount_desc", "created_at_desc")
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    companion object {
        private val logger = LogManager.getLogger(OrderControllerIntegrationTests::class.java)
    }
}