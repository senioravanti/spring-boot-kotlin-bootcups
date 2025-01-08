package ru.manannikov.bootcupsbot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.format.support.FormattingConversionService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Configuration
class FormatterServiceConfig {
    @Value("\${telegram.validation.date-formats}")
    lateinit var validDateFormats: List<String>

    @Bean
    fun conversionService(): FormattingConversionService = DefaultFormattingConversionService(true).apply {
        addFormatter(localDateFormatter())
    }

    private fun localDateFormatter(): Formatter<LocalDate> = object : Formatter<LocalDate> {
        override fun print(source: LocalDate, locale: Locale): String = source.toString()

        override fun parse(source: String, locale: Locale): LocalDate {
            val formatPatterns = validDateFormats.joinToString("") { "[$it]" }
            val formatter = DateTimeFormatter.ofPattern(formatPatterns)
            return LocalDate.parse(source, formatter)
        }

    }
}