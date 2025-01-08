package ru.manannikov.bootcupsbot.services.impl.handlers.registration


import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.ConversionFailedException
import org.springframework.format.support.FormattingConversionService
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.ClientDtoValidationService
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.services.impl.handlers.registration.AskPhoneNumberRegistrationHandler.Companion
import ru.manannikov.bootcupsbot.validation.AskBirthdateGroup
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Service("askBirthDateRegistrationHandler")
class AskBirthDateRegistrationHandler(
    @Qualifier("clientDtoValidationService")
    private val clientDtoValidationService: ClientDtoValidationService,
    private val conversionService: FormattingConversionService
)
    : UpdateHandler
{
    @Value("\${telegram.validation.date-formats}")
    lateinit var validDateFormats: List<String>;

    override fun apply(messageDto: MessageDto): MessageResponse {
        val clientDto = messageDto.client

        val messageResponse = MessageResponse().apply {
            chatId = clientDto.chatId
        }

        try {
            clientDto.birthday = conversionService.convert(messageDto.command, LocalDate::class.java)
        } catch (ex: ConversionFailedException) {
            logger.error("Ошибка при парсинге даты {}, см.:\n{}", messageDto.command, ex.toString())

            return messageResponse.apply {
                messageCode = "messages.formating.client-dto.birthday"
                args = arrayOf(validDateFormats.joinToString(", "))
            }
        }

        clientDtoValidationService.validate(
            clientDto,
            RegistrationState.ASK_PHONE_NUMBER,
            MESSAGE_CODE,
            AskBirthdateGroup::class.java
        ).also {
            messageResponse.messageCode = it
            if (it == MESSAGE_CODE) {
                logger.info(
                    "Дата рождения {} пользователя с chatId = {} успешно зарегистрирована",
                    clientDto.birthday,
                    clientDto.chatId
                )
            }
        }

        return messageResponse
    }

    companion object {
        private const val MESSAGE_CODE = "messages.command.registration.ask-birthday.valid"
        private val logger = LogManager.getLogger(AskBirthDateRegistrationHandler::class.java)
    }
}