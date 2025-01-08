package ru.manannikov.bootcupsbot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.format.support.FormattingConversionService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@TestConfiguration
class FormatterServiceConfig {
    val validDateFormats: List<String> = listOf(
        "dd.MM.yyyy",
        "dd/MM/yyyy",
        "yyyy-MM-dd",
        "yyyy/MM/dd",
        "yyyy.dd.MM"
    )

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