package ru.manannikov.bootcupsbot.services.impl.handlers.callback


import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.services.CommandHandler
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.utils.Constants.CALLBACK_ASK_REGISTER_NO
import ru.manannikov.bootcupsbot.utils.Constants.CALLBACK_ASK_REGISTER_YES

@Service
class CallbackHandler(
    @Qualifier("acceptStartRegistrationHandler")
    private val acceptStartRegistrationHandler: CommandHandler
) : UpdateHandler {
    // В идеале предыдущее сообщение должно редактироваться
    private val denyStartRegistrationHandler = CommandHandler {
        clientDto -> MessageResponse().apply {
            chatId = clientDto.chatId
            messageCode = "messages.command.registration.ask-registration.no.answer"
        }
    }

    @PostConstruct
    fun init() {
        callbackHandlers[CALLBACK_ASK_REGISTER_YES] =
            acceptStartRegistrationHandler

        callbackHandlers[CALLBACK_ASK_REGISTER_NO] =
            denyStartRegistrationHandler
    }

    override fun apply(messageDto: MessageDto): MessageResponse {
        return callbackHandlers[messageDto.command]!!.apply(messageDto.client)
    }

    companion object {
        private val callbackHandlers: MutableMap<String, CommandHandler?> = HashMap<String, CommandHandler?>()
    }
}
