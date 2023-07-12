package com.john.lotto.auth.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Configuration
class AuthRouter(
    private val authHandler: AuthHandler
) {

    @Bean
    fun authRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/auth/authorize", authHandler::authorize)
            GET("/auth/token", authHandler::token)
            GET("/auth/refresh", authHandler::refresh)
        }
    }
}