package ru.manannikov.bootcupsbot.services.impl.handlers.registration


import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.ClientDtoValidationService
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.services.impl.handlers.registration.AskPhoneNumberRegistrationHandler.Companion
import ru.manannikov.bootcupsbot.utils.Constants.BONUS_CARD_INFO_COMMAND
import ru.manannikov.bootcupsbot.validation.AskEmailGroup

@Service("askEmailRegistrationHandler")
class AskEmailRegistrationHandler(
    @Qualifier("clientDtoValidationService")
    private val clientDtoValidationService: ClientDtoValidationService
)
    : UpdateHandler
{
    override fun apply(messageDto: MessageDto): MessageResponse {
        val clientDto = messageDto.client.apply {
            email = messageDto.command
        }

        val messageResponse = MessageResponse().apply {
            chatId = clientDto.chatId
        }

        clientDtoValidationService.validate(
            clientDto,
            RegistrationState.REGISTERED,
            MESSAGE_CODE,
            AskEmailGroup::class.java
        ).also {
            messageResponse.messageCode = it
            if (it == MESSAGE_CODE) {
                messageResponse.args = arrayOf(clientDto.name, BONUS_CARD_INFO_COMMAND)
                logger.info(
                    "Пользователь c email {} и chatId = {} успешно зарегистрован",
                    clientDto.email,
                    clientDto.chatId
                )
            }
        }

        return messageResponse
    }

    companion object {
        private const val MESSAGE_CODE = "messages.command.registration.ask-email.valid"
        private val logger = LogManager.getLogger(AskEmailRegistrationHandler::class.java)
    }
}
