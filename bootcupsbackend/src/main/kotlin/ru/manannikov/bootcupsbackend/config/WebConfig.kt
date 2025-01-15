package ru.manannikov.bootcupsbackend.config

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig {
    @Value("\${app.frontend-origin}")
    lateinit var frontendOrigin: String

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object: WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            logger.debug("frontend origin: {}", frontendOrigin)
            registry
                .addMapping("/**")
                .allowedOrigins(frontendOrigin)
                .allowedMethods("*")
        }
    }

    companion object {
        private val logger = LogManager.getLogger(WebConfig::class.java)
    }
}