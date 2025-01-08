package ru.manannikov.bootcupsbot.config

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.context.MessageSource
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.validation.AskBirthdateGroup
import ru.manannikov.bootcupsbot.validation.AskEmailGroup
import ru.manannikov.bootcupsbot.validation.AskNameGroup
import ru.manannikov.bootcupsbot.validation.AskPhoneNumberGroup
import java.time.LocalDate
import java.util.*

class ValidatorConfigTest {
    @Test
    fun testClientDtoValidation() {
        AnnotationConfigApplicationContext(ValidationAutoConfiguration::class.java, MessageSourceConfig::class.java).use { ctx ->

            val validator = ctx.getBean("defaultValidator", Validator::class.java)
            assertNotNull(validator) { "validator не может быть null" }

            val clientDto = ClientDto(825898690).apply {
                name = ""
                email = "anton&666.gmail.com"
                phoneNumber = "- 100 500 400 23 32 43"
                birthday = LocalDate.of(2020, 8, 12)
            }

            val messageSource = ctx.getBean("messageSource", MessageSource::class.java)
            assertNotNull(messageSource) { "messageSource не может быть null" }

            val violations = validator.validate(clientDto, AskNameGroup::class.java, AskEmailGroup::class.java, AskPhoneNumberGroup::class.java, AskBirthdateGroup::class.java)
            assertEquals(4, violations.size)
            listViolations(violations, messageSource)
        }
    }

    companion object {
        private val logger = LogManager.getLogger(ValidatorConfigTest::class.java)
        private fun listViolations(violations: Set<ConstraintViolation<ClientDto>>, messageSource: MessageSource) {
            val logMessage = StringBuilder("violations:\n")
            violations.forEach {
                logMessage
                    .append("Validation error for property $it.propertyPath, with value: $it.invalidValue, with message: ${messageSource.getMessage(it.message, null, Locale.getDefault())}")
//                    .append("Validation error for property ${it.propertyPath}, with value: ${it.invalidValue}, with message: ${it.message}")
                    .append("\n\n")
            }
            logger.info(logMessage)
        }
    }
}