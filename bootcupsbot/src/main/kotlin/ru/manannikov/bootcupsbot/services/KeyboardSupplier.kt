package ru.manannikov.bootcupsbot.services

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

fun interface KeyboardSupplier {
    fun apply(languageCode: String): ReplyKeyboard
}