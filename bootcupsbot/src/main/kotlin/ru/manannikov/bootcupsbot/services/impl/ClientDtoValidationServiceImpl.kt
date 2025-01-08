package ru.manannikov.bootcupsbot.services.impl

import jakarta.validation.Validator
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.BootcupsClientService
import ru.manannikov.bootcupsbot.services.ClientDtoValidationService

@Service("clientDtoValidationService")
class ClientDtoValidationServiceImpl
(
    private val validator: Validator,
    private val bootcupsClientService: BootcupsClientService
)
    : ClientDtoValidationService
{
    override fun validate(clientDto: ClientDto, nextRegistrationState: RegistrationState, messageCode: String, validationGroup: Class<*>): String {
        val violations = validator.validate(clientDto, validationGroup)

        when (violations.size) {
            0 -> {
                clientDto.registrationState = nextRegistrationState
                bootcupsClientService.registerOrUpdateClient(clientDto)
                logger.info("Объект успешно прошел валидацию: {}", clientDto)
                return messageCode
            }

            1 -> {
                val violation = violations.iterator().next()
                logger.info("Ошибка валидации поля {}: {}.", violation.propertyPath, violation.invalidValue)
                return violation.message
            }

            else -> throw IllegalArgumentException("Методу передан объект, который не прошел предыдущие этапы валидации")
        }
    }

    companion object {
        private val logger = LogManager.getLogger(ClientDtoValidationServiceImpl::class.java)
    }
}