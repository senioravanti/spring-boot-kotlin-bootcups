package ru.manannikov.bootcupsbot.services.impl.handlers.registration


import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.ClientDtoValidationService
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.validation.AskNameGroup

@Service("askNameRegistrationHandler")
class AskNameRegistrationHandler(
    @Qualifier("clientDtoValidationService")
    private val clientDtoValidationService: ClientDtoValidationService
) : UpdateHandler {

    override fun apply(messageDto: MessageDto): MessageResponse {
        val clientDto = messageDto.client.apply {
            name = messageDto.command
        }

        val messageResponse = MessageResponse().apply {
            chatId = clientDto.chatId
        }

        clientDtoValidationService.validate(
            clientDto,
            RegistrationState.ASK_BIRTHDATE,
            MESSAGE_CODE,
            AskNameGroup::class.java
        ).also {
            messageResponse.messageCode = it
            if (it == MESSAGE_CODE) {
                messageResponse.args = arrayOf(messageDto.command)
                logger.info("Пользователю с chatId = {} успешно присвоено имя: {}", clientDto.chatId, clientDto.name)
            }
        }

        return messageResponse
    }

    companion object {
        private const val MESSAGE_CODE = "messages.command.registration.ask-name"
        private val logger = LogManager.getLogger(AskNameRegistrationHandler::class.java)
    }
}
