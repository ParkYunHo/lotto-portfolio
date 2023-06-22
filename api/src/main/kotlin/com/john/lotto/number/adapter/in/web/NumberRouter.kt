package com.john.lotto.number.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.06.22
 */
@Configuration
class NumberRouter(
    private val numberHandler: NumberHandler
) {

    @Bean
    fun numberRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/api/number", numberHandler::findLottoNumber)
        }
    }
}