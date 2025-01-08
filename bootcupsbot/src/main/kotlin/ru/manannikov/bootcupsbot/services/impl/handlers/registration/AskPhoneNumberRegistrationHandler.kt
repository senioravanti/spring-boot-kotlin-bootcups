package ru.manannikov.bootcupsbot.services.impl.handlers.registration


import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.ClientDtoValidationService
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.validation.AskPhoneNumberGroup

@Service("askPhoneNumberRegistrationHandler")
class AskPhoneNumberRegistrationHandler(
    @Qualifier("clientDtoValidationService")
    private val clientDtoValidationService: ClientDtoValidationService
)
    : UpdateHandler
{
    override fun apply(messageDto: MessageDto): MessageResponse {
        val clientDto = messageDto.client.apply {
            phoneNumber = messageDto.command
        }

        val messageResponse = MessageResponse().apply {
            chatId = clientDto.chatId
        }

        clientDtoValidationService.validate(
            clientDto,
            RegistrationState.ASK_EMAIL,
            MESSAGE_CODE,
            AskPhoneNumberGroup::class.java
        ).also {
            messageResponse.messageCode = it
            if (it == MESSAGE_CODE)
                logger.info(
                    "Номер телефона {} пользователя с chatId = {} успешно зарегистрирован",
                    clientDto.phoneNumber,
                    clientDto.chatId
                )
        }

        return messageResponse
    }

    companion object {
        private const val MESSAGE_CODE = "messages.command.registration.ask-phone-number.valid"
        private val logger = LogManager.getLogger(AskPhoneNumberRegistrationHandler::class.java)
    }
}
