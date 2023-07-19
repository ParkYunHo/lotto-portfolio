package com.john.lotto.member.adapter.`in`.web

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
class MemberRouter(
    private val memberHandler: MemberHandler
) {

    @Bean
    fun memberRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/api/member", memberHandler::register)
            GET("/api/member", memberHandler::search)
            DELETE("/api/member", memberHandler::delete)
        }
    }
}