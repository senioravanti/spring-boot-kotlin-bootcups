package ru.manannikov.bootcupsbot.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@TestConfiguration
class MessageSourceConfig {
    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasenames("messages/messages")
        messageSource.setFallbackToSystemLocale(false)
        messageSource.setDefaultEncoding("UTF-8")

        return messageSource
    }
}