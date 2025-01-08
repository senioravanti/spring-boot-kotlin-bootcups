package ru.manannikov.bootcupsbot.services.impl


import org.springframework.context.MessageSource
import org.springframework.lang.Nullable
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.manannikov.bootcupsbot.services.KeyboardService
import ru.manannikov.bootcupsbot.utils.Constants.BONUS_CARD_COMMAND
import ru.manannikov.bootcupsbot.utils.Constants.CALLBACK_ASK_REGISTER_NO
import ru.manannikov.bootcupsbot.utils.Constants.CALLBACK_ASK_REGISTER_YES
import ru.manannikov.bootcupsbot.utils.Constants.NO_BREAK_SPACE
import java.util.*

@Service
class KeyBoardServiceImpl(
    private val messageSource: MessageSource
) : KeyboardService {

    override fun registerKeyboard(languageCode: String): ReplyKeyboard {
        val buttonYes: InlineKeyboardButton = InlineKeyboardButton.builder()
            .text(
                messageSource.getMessage(
                    "messages.command.registration.ask-registration.yes",
                    null,
                    Locale.of(languageCode)
                )
            )
            .callbackData(CALLBACK_ASK_REGISTER_YES)
            .build()
        val buttonNo: InlineKeyboardButton = InlineKeyboardButton.builder()
            .text(
                loadMessage("messages.command.registration.ask-registration.no", languageCode)
            )
            .callbackData(CALLBACK_ASK_REGISTER_NO)
            .build()
        val keyboard: List<InlineKeyboardRow> = listOf(InlineKeyboardRow(buttonYes, buttonNo))

        return InlineKeyboardMarkup(keyboard)
    }


    override fun mainKeyboard(languageCode: String): ReplyKeyboard {
        val bonusCardInfoRow = KeyboardRow(
            loadMessage(
                "messages.command.bonus-card.text",
                arrayOf(BONUS_CARD_COMMAND, NO_BREAK_SPACE),
                languageCode
            )
        )

        return ReplyKeyboardMarkup.builder()
            .resizeKeyboard(true)
            .isPersistent(false)
            .oneTimeKeyboard(true)
            .keyboard(
                listOf(bonusCardInfoRow)
            )
            .build()
    }

    private fun loadMessage(messageCode: String, @Nullable languageCode: String?): String {
        return loadMessage(messageCode, null, languageCode)
    }

    private fun loadMessage(
        messageCode: String, @Nullable args: Array<Any>?,
        @Nullable languageCode: String?
    ): String {
        return messageSource.getMessage(
            messageCode,
            args,
            if (languageCode != null) Locale.of(languageCode) else Locale.getDefault()
        )
    }
}
