package com.john.lotto.scrap.adatper.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.07.19
 */
@Configuration
class ScrapRouter(
    private val scrapHandler: ScrapHandler
) {

//    @Bean
//    fun scrapRouterFunction(): RouterFunction<ServerResponse> = router {
//        accept(MediaType.APPLICATION_JSON).nest {
////            POST("/api/member", memberHandler::register)
//        }
//    }
}