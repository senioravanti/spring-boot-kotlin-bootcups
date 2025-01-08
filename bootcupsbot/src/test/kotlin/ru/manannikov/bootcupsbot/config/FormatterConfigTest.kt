package ru.manannikov.bootcupsbot.config

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.format.support.FormattingConversionService
import java.time.LocalDate

// Минимализм во всем, не надо слишком сильно париться по пустякам
class FormatterConfigTest {
    @Test
    fun testDateFormatter() {
        AnnotationConfigApplicationContext(FormatterServiceConfig::class.java).use { ctx ->

            val conversionService = ctx.getBean("conversionService", FormattingConversionService::class.java)
            assertNotNull(conversionService) { "conversionService не может быть null" }

            mockDates.forEach {
                assertEquals(sample, conversionService.convert(it, LocalDate::class.java))
            }

        }
    }

    companion object {
        private val mockDates = arrayOf(
            "14.08.2004",
            "14/08/2004",
            "2004-08-14",
            "2004.14.08"
        )
        private val sample = LocalDate.of(2004, 8, 14)
    }
}