package ru.manannikov.bootcupsbot.utils

import java.time.format.DateTimeFormatter


object Constants {
    const val START_COMMAND: String = "/start"
    const val BONUS_CARD_INFO_COMMAND: String = "/bonus_card"
    const val HELP_COMMAND: String = "/help"
    const val REGISTER_COMMAND: String = "/register"

    const val PARSE_MODE: String = "markdown"

    const val CALLBACK_ASK_REGISTER_YES: String = "CALLBACK_ASK_REGISTER_YES"
    const val CALLBACK_ASK_REGISTER_NO: String = "CALLBACK_ASK_REGISTER_NO"

    const val NO_BREAK_SPACE: String = "\u00A0"
    const val BONUS_CARD_COMMAND: String = "\ud83d\udcb3"

    val FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")
}
