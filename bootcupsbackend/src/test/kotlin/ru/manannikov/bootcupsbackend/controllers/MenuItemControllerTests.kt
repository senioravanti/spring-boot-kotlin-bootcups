package ru.manannikov.bootcupsbackend.controllers

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

import ru.manannikov.bootcupsbackend.config.AppConfig
import ru.manannikov.bootcupsbackend.services.MenuItemService
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PAGE_SIZE
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PRODUCT_NAME
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.SORT
import ru.manannikov.bootcupsbackend.utils.MessageUtils

@Import(value = [MessageUtils::class, AppConfig::class])
@WebMvcTest(
    controllers = [MenuItemController::class],
)
class MenuItemControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var menuItemService: MenuItemService

    @Test
    fun testAll() {
        mockMvc.get("/api/v1/menu/") {
            contextPath = "/api"

            param(SORT, "price_asc", "makes_desc")
            param(PRODUCT_NAME, "Латте")
            param(MENU_ITEM_PRICE_MIN, "200.00")
            param(CATEGORY, "healthy_nutrition", "compote", "sourdough_bread")

            param(PAGE_SIZE, "10")
            param(PAGE_NUMBER, "1")
        }
        .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }


    companion object {
        private val logger = LogManager.getLogger(MenuItemControllerTests::class.java)
    }
}