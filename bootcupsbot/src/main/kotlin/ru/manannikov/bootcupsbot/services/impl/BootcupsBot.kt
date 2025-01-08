package ru.manannikov.bootcupsbot.services.impl


import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update
import ru.manannikov.bootcupsbot.dto.MessageDto
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.services.BootcupsClientService
import ru.manannikov.bootcupsbot.services.impl.handlers.callback.CallbackHandler
import ru.manannikov.bootcupsbot.services.impl.handlers.message.UpdateMessagesHandler

/**
 * Точка входа в процесс обработки сообщений
 */
@Service
class BootcupsBot(
    val updateMessagesHandler: UpdateMessagesHandler,
    val bootcupsClientService: BootcupsClientService,
    val messageSender: MessageSender,
    val callbackHandler: CallbackHandler
)
    : LongPollingSingleThreadUpdateConsumer
{

    override fun consume(update: Update) {
        // Сообщение может содержать звук, а не текст
        lateinit var messageResponse: MessageResponse
        val languageCode = "ru"

        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text

            logger.info("Обработка сообщения : {} ", messageText)

            val messageDto = MessageDto().apply {
                command = messageText
                client = bootcupsClientService.loadClientByChatId(
                    update.message.chatId
                )
                replyToMessageId = update.message.messageId
            }

            messageResponse = updateMessagesHandler.apply(messageDto)

        } else if (update.hasCallbackQuery()) {
            val callbackData = update.callbackQuery.data
            val chatId = update.callbackQuery.message.chatId
            logger.info(
                "Обработка нажатия КНОПКИ : {}, прикрепленной к СООБЩЕНИЮ : {}",
                callbackData,
                update.callbackQuery.message.messageId
            )

            val messageDto = MessageDto().apply {
                command = callbackData
                client = bootcupsClientService.loadClientByChatId(chatId)
            }

            messageResponse = callbackHandler.apply(messageDto)
        }

        messageSender.sendMessage(
            messageResponse,
            languageCode
        )
    }

    companion object {
        private val logger = LogManager.getLogger(BootcupsBot::class.java)
    }
}
