package ru.manannikov.bootcupsbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
class AppConfig {
    companion object {
        @JvmStatic
        @Bean
        fun propertyPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer()
    }
}