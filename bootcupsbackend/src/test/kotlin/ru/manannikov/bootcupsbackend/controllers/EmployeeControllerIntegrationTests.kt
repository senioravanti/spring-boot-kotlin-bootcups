package ru.manannikov.bootcupsbackend.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import jakarta.validation.Validator
import org.apache.logging.log4j.LogManager
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
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
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.dto.EmployeeRequest
import ru.manannikov.bootcupsbackend.dto.PaginationResponse
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.FIRST_NAME
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.LAST_NAME
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.MIDDLE_NAME
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.EMPLOYEE_ROLE_NAME
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT
import kotlin.test.assertNotEquals

@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG)
@SpringBootTest
class EmployeeControllerIntegrationTests : TestcontainersTest() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var validator: Validator

    lateinit var objectWriter: ObjectWriter

    @BeforeEach
    fun setUp() {
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter()
    }

    @Test
    fun testFindAll() {
        mockMvc
            .get("/api/v1/employee/") {
                contextPath = "/api"

                param(PAGE_SIZE, "3")
                param(PAGE_NUMBER, "0")

                param(FIRST_NAME, "Рома")
                param(EMPLOYEE_ROLE_NAME, "BEBRUS")

                param(SORT, "first_name_desc", "last_name_asc")
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.currentPageNumber") { value(0) }
                jsonPath("$.totalElements") { value(2) }
                jsonPath("$.totalPages") { value(1) }
            }
    }

    @Test
    @DisplayName("Проверяю настройки валидации и работу RestExceptionHandler")
    fun testCreate() {
        val employee = EmployeeRequest(
            null,
            "Батонский",
            "Антон",
            null,

            "white_senioravanti",
            "aZ*0&Pa",

            "anton3baton3@g.cn",
            "+7 900 300 34-34",
            "barista"
        )
        val employeeJson = objectWriter.writeValueAsString(employee)
        mockMvc
            .post("/v1/employee/") {
                contentType = MediaType.APPLICATION_JSON
                content = employeeJson
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }

        mockMvc
            .get("/api/v1/employee/") {
                contextPath = "/api"

                param(PAGE_SIZE, "6")
                param(PAGE_NUMBER, "0")

                param(SORT, "last_name_desc", "first_name_desc")
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.totalElements") { value(6) }
            }
    }

    @Test
    @DisplayName("Должен обновлять данные сущ. employee")
    fun testUpdate() {
        val result = mockMvc
            .get("/api/v1/employee/") {
                contextPath = "/api"

                param(PAGE_SIZE, "1")
                param(PAGE_NUMBER, "0")

                param(LAST_NAME, "Соколов")
                param(FIRST_NAME, "Роман")
                param(MIDDLE_NAME, "Добрыниевич")
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        logger.debug("result:\n{}", result)
        assertThat(result, `is`(not(nullValue())))
        assertThat(result, `is`(not(emptyString())))

        val employee = objectMapper.readValue(result, object: TypeReference<PaginationResponse<EmployeeRequest>>(){}).content.first()
        logger.debug(employee)

        val updatedEmployee = EmployeeRequest(
            id = employee.id,
            lastName = employee.lastName,
            firstName = employee.lastName,
            middleName = null,

            username = "sokol_man666",
            password = employee.password,

            email = "anton_3@g.cn",
            phoneNumber = employee.phoneNumber,
            roleKey = "barista"
        )

        val violations = validator.validate(updatedEmployee)
        logger.debug(violations)
        assertNotEquals(0, violations.size)

        mockMvc
            .put("/v1/employee/${updatedEmployee.id}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectWriter.writeValueAsString(updatedEmployee)
            }
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
            }
    }

    companion object {
        private val logger = LogManager.getLogger(EmployeeControllerIntegrationTests::class.java)
    }
}