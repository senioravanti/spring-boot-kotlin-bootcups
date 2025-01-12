package ru.manannikov.bootcupsbackend.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class AppConfig {
    @Bean
    fun messageSource(): MessageSource = ReloadableResourceBundleMessageSource().apply {
        setBasenames(
            "classpath:messages/validation-messages",
            "classpath:messages/ui-messages",
            "classpath:messages/error-messages"
        )
        setDefaultEncoding("UTF-8")
        setFallbackToSystemLocale(false)
        setUseCodeAsDefaultMessage(false)
    }

    @Bean
    fun validator(): LocalValidatorFactoryBean = LocalValidatorFactoryBean().apply {
        setValidationMessageSource(messageSource())
    }
}