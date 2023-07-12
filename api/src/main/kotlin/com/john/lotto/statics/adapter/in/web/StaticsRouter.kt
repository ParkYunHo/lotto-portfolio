package com.john.lotto.statics.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.07.12
 */
@Configuration
class StaticsRouter(
    private val staticsHandler: StaticsHandler
) {

    @Bean
    fun staticsRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/api/statics/period", staticsHandler::findPeriod)
            GET("/api/statics/number", staticsHandler::findDrwtNo)
            GET("/api/statics/rank", staticsHandler::findWinAmount)
        }
    }
}