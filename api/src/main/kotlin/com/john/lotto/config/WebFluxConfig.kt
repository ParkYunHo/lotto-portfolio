package com.john.lotto.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

/**
 * @author yoonho
 * @since 2023.06.22
 */
@Configuration
@EnableWebFlux
class WebFluxConfig: WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/webjars/**")
            .allowedOrigins("http://localhost:8080")
            .allowedMethods("GET", "POST")
    }
}