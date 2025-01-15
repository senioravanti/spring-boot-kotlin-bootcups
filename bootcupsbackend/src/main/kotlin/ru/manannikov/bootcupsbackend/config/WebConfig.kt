package ru.manannikov.bootcupsbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig {
    @Value("\${app.frontend-origin}")
    lateinit var frontendOrigin: String

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object: WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/api/**")
                .allowedOrigins(frontendOrigin)
                .allowedMethods("*")
        }
    }
}