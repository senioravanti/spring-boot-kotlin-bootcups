package ru.manannikov.bootcupsbot.services

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

interface KeyboardService {
    fun registerKeyboard(languageCode: String): ReplyKeyboard
    fun mainKeyboard(languageCode: String): ReplyKeyboard
}