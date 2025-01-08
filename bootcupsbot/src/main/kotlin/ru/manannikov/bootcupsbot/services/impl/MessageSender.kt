package ru.manannikov.bootcupsbot.services.impl

import org.apache.logging.log4j.LogManager
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.manannikov.bootcupsbot.dto.MessageResponse
import ru.manannikov.bootcupsbot.utils.Constants.PARSE_MODE
import java.util.*


@Service
class MessageSender(val telegramClient: TelegramClient, val messageSource: MessageSource) {

    fun sendMessage(messageResponse: MessageResponse, languageCode: String?) {
        val messageText = messageSource.getMessage(
            messageResponse.messageCode,
            messageResponse.args,
            if (languageCode != null) Locale.of(languageCode) else Locale.getDefault()
        )

        val messageToSend = SendMessage.builder()
            .chatId(messageResponse.chatId)
            .text(messageText)
            .parseMode(PARSE_MODE)
            .build()

        if (messageResponse.keyboard != null) messageToSend.replyMarkup =
            messageResponse.keyboard!!.apply(languageCode!!)

        if (messageResponse.replyToMessageId != null) messageToSend.replyToMessageId = messageResponse.replyToMessageId

        try {
            telegramClient.execute(messageToSend)
        } catch (ex: TelegramApiException) {
            logger.error("Ошибка при отправке сообщения \"{}\" : {}", messageText, ex.toString())
        }
    }

    companion object {
        private val logger = LogManager.getLogger(MessageSender::class.java)
    }
}
