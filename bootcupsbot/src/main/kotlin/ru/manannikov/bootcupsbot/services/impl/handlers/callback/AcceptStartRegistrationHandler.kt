package ru.manannikov.bootcupsbot.services.impl.handlers.callback


import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.ClientDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.enums.RegistrationState
import ru.manannikov.bootcupsbot.services.BootcupsClientService
import ru.manannikov.bootcupsbot.services.CommandHandler

/**
 * Срабатывает при нажатии на inline-кнопку "да" В предложении зарегистрироваться
 */
@Service("acceptStartRegistrationHandler")
class AcceptStartRegistrationHandler(
    private val bootcupsClientService: BootcupsClientService
) : CommandHandler {

    override fun apply(clientDto: ClientDto): MessageResponse {
        logger.debug("Начинаю регистрацию клиента с chatId = {}", clientDto.chatId)
        clientDto.registrationState = RegistrationState.ASK_NAME

        bootcupsClientService.registerOrUpdateClient(clientDto)

        return MessageResponse().apply {
            chatId = clientDto.chatId
            messageCode = "messages.command.registration.ask-registration.yes.answer"
        }
    }

    companion object {
        private val logger = LogManager.getLogger(AcceptStartRegistrationHandler::class.java)
    }
}
