package ru.manannikov.bootcupsbot.services.impl.handlers.message


import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.services.CommandHandler
import ru.manannikov.bootcupsbot.services.KeyboardService
import ru.manannikov.bootcupsbot.services.KeyboardSupplier
import ru.manannikov.bootcupsbot.services.UpdateHandler
import ru.manannikov.bootcupsbot.utils.Constants.BONUS_CARD_COMMAND
import ru.manannikov.bootcupsbot.utils.Constants.BONUS_CARD_INFO_COMMAND
import ru.manannikov.bootcupsbot.utils.Constants.HELP_COMMAND
import ru.manannikov.bootcupsbot.utils.Constants.START_COMMAND
import ru.manannikov.bootcupsbot.utils.ObjectUtils.extractCommandFromKeyboardButtonText
import java.util.function.Function

/**
 * Отправляет сообщение зарегистрированному клиенту
 * Обработчики команд должны просто вернуть строку в ответ на команду, а этот сервис уже сам разберется как отправлять сообщение
 */
@Service("registeredClientCommandsHandler")
class RegisteredClientCommandsHandler(
    private val keyboardService: KeyboardService,

    @Qualifier("bonusCardCommandHandler")
    private val bonusCardCommandHandler: CommandHandler
)
    : UpdateHandler
{
    private val startCommandHandler = CommandHandler {
        clientDto -> MessageResponse().apply {
            chatId = clientDto.chatId
            messageCode = "messages.command.start.registered"
            args = arrayOf(clientDto.name)
        }
    }

    private val helpCommandHandler = CommandHandler {
        clientDto -> MessageResponse().apply {
            chatId = clientDto.chatId
            messageCode = "messages.command.start.registered"
            args = arrayOf(BONUS_CARD_INFO_COMMAND, HELP_COMMAND)
        }
    }

    @PostConstruct
    fun init() {
        commandHandlers[START_COMMAND] = startCommandHandler
        commandHandlers[HELP_COMMAND] = helpCommandHandler
        commandHandlers[BONUS_CARD_COMMAND] = bonusCardCommandHandler
        commandHandlers[BONUS_CARD_INFO_COMMAND] = bonusCardCommandHandler
    }

    override fun apply(messageDto: MessageDto): MessageResponse {
        val commandHandler: CommandHandler? = if (messageDto.command.startsWith("/"))
            // Была введена команда (сообщение с префиксом "/")
            commandHandlers[messageDto.command.split(" ")[0]]
        else
            // Была нажата кнопка
            commandHandlers[extractCommandFromKeyboardButtonText(messageDto.command)]

        return commandHandler
            ?.apply(messageDto.client)?.apply {
                    keyboard = KeyboardSupplier { keyboardService.mainKeyboard(it) }
                    replyToMessageId = messageDto.replyToMessageId
                }
            ?: CommandHandler {
                MessageResponse().apply {
                    chatId = it.chatId
                    messageCode = "messages.command.not-recognized"
                    replyToMessageId = messageDto.replyToMessageId
                }
            }
                .apply(messageDto.client)
    }

    companion object {
        private val commandHandlers: MutableMap<String, CommandHandler?> = HashMap<String, CommandHandler?>()
    }
}