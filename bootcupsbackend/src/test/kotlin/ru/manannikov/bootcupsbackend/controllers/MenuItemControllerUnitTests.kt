package ru.manannikov.bootcupsbackend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import ru.manannikov.bootcupsbackend.config.AppConfig
import ru.manannikov.bootcupsbackend.config.WebConfig
import ru.manannikov.bootcupsbackend.dto.MenuItemRequest
import ru.manannikov.bootcupsbackend.dto.MenuItemResponse
import ru.manannikov.bootcupsbackend.dto.ProductDto
import ru.manannikov.bootcupsbackend.dto.UnitDto
import ru.manannikov.bootcupsbackend.entities.RoleEntity
import ru.manannikov.bootcupsbackend.services.*
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.CATEGORY
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.MENU_ITEM_PRICE_MIN
import ru.manannikov.bootcupsbackend.services.MenuItemService.Companion.PRODUCT_NAME
import ru.manannikov.bootcupsbackend.utils.ModelConverter
import ru.manannikov.bootcupsbackend.utils.PAGE_NUMBER
import ru.manannikov.bootcupsbackend.utils.PAGE_SIZE
import ru.manannikov.bootcupsbackend.utils.SORT
import java.awt.SystemColor.menu
import java.math.BigDecimal

/**
 * Должен тестировать исключительно ответы на запросы при определенных условиях
 * При работе с модульными тестами конфигурация логера ложится на разработчика
 */
@ExtendWith(MockitoExtension::class)
@TestPropertySource(
    locations = ["classpath:application.yaml"]
)
@SpringJUnitWebConfig(classes = [AppConfig::class, WebConfig::class])
class MenuItemControllerUnitTests {

    @Mock
    lateinit var menuItemService: MenuItemService
    @Mock
    lateinit var orderService: OrderService

    @Mock
    lateinit var dictionaryService: DictionaryService<RoleEntity>
    @Mock
    lateinit var productService: ProductService
    @Mock
    lateinit var unitService: UnitService

    @Autowired
    lateinit var messageSource: MessageSource

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setupMockMvc() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(
                MenuItemController(
                    menuItemService,
                    ModelConverter(dictionaryService, productService, unitService, menuItemService, orderService, messageSource)
                )
            )
            .setControllerAdvice(RestExceptionHandler(messageSource))
            .setValidator(LocalValidatorFactoryBean())
            .build()
    }

    @Test
    fun testFindAll() {
        mockMvc.get("/api/v1/menu/") {
            contextPath = "/api"

            param(SORT, "price_asc", "makes_desc")
            param(PRODUCT_NAME, "Латте")
            param(MENU_ITEM_PRICE_MIN, "200.00")
            param(CATEGORY, "healthy_nutrition", "compote", "sourdough_bread")

            param(PAGE_SIZE, "10")
            param(PAGE_NUMBER, "1")
        }
        .andDo { print() }
        .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun testCreateInvalidMenuItem() {
        logger.info("Тестирую валидацию")
        val objectMapper = ObjectMapper()
        val invalidMenuItem = MenuItemRequest(
            null,

            makes = 0,
            price = BigDecimal("0.0"),
            topping = null,
            imageUri = null,

            productId = 1,
            unitId = 1
        )

        mockMvc.post("/v1/menu/") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidMenuItem)
        }
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
                content { contentType(MediaType.APPLICATION_PROBLEM_JSON) }
            }
    }

    companion object {
        private val logger = LogManager.getLogger(MenuItemControllerUnitTests::class.java)
    }
}