package ru.manannikov.bootcupsbackend.controllers

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import ru.manannikov.bootcupsbackend.TestcontainersTest
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT

@AutoConfigureMockMvc
@SpringBootTest
class MenuItemControllerIntegrationTests : TestcontainersTest() {
    @Autowired
    lateinit var mockMvc: MockMvc

    /**
     * Jackson по умолчанию при сериализации не меняет регистр с-в dto
     * Объем должен убывать при равных ценах
     */
    @Test
    @DisplayName("Должен возвращать 1-ю страницу, сод. 2 представления позиций меню, из 6 найденных.")
    fun testFindFindAll() {
        logger.info("Отправляю GET запрос ...")

        val categories = arrayOf("breakfast", "backing", "salad")

        mockMvc.get("/api/v1/menu/") {
            contextPath = "/api"

            param(SORT, "price_asc", "makes_desc")
            param(MENU_ITEM_PRICE_MIN, "100.00")
            param(CATEGORY, *categories)

            param(PAGE_SIZE, "4")
            param(PAGE_NUMBER, "1")
        }
        .andDo { print() }
        .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.currentPageNumber") {
                value(1)
            }
            jsonPath("$.totalElements") {
                value(6)
            }
            jsonPath("$.totalPages") {
                value(2)
            }
        }
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemControllerIntegrationTests::class.java)
    }
}