package ru.manannikov.bootcupsbot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.manannikov.bootcupsbot.services.impl.BootcupsBot

@Configuration
class TelegramBotConfig {
    @Value("\${telegram.bot.name}")
    private val name: String? = null

    @Value("\${telegram.bot.token}")
    private val token: String? = null

    @Bean
    @Throws(TelegramApiException::class)
    fun telegramBotsApplication(
        bootcupsBot: BootcupsBot?
    ): TelegramBotsLongPollingApplication {
        val bot = TelegramBotsLongPollingApplication()
        bot.registerBot(token, bootcupsBot)
        return bot
    }

    @Bean
    fun telegramClient(): TelegramClient {
        return OkHttpTelegramClient(token)
    }
}
