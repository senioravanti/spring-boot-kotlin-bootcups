package ru.manannikov.bootcupsbackend.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import ru.manannikov.bootcupsbackend.services.EmployeeService.Companion.ROLE_NAME
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT

@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerIntegrationTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun testFindAll() {
        mockMvc.get("/api/v1/menu/") {
            contextPath = "/api"

            param(PAGE_SIZE, "2")
            param(PAGE_NUMBER, "1")

            param(ROLE_NAME, "BARISTA")

            param(SORT, "first_name_desc", "last_name_asc")
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

            }
    }
}