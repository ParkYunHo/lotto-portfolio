package com.john.lotto.auth.adapter.`in`.web

import com.john.lotto.auth.application.dto.ResultTokenInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
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
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/auth/authorize",
                method = [RequestMethod.GET],
                beanClass = AuthHandler::class,
                beanMethod = "authorize",
                operation = Operation(
                    tags = ["인증관리"],
                    summary = "인가코드 요청",
                    operationId = "authorize"
                )
            ),
            RouterOperation(
                path = "/auth/token",
                method = [RequestMethod.GET],
                beanClass = AuthHandler::class,
                beanMethod = "token",
                operation = Operation(
                    tags = ["인증관리"],
                    summary = "토큰 발급",
                    operationId = "token",
                    parameters = [
                        Parameter(
                            name = "state",
                            description = "소셜로그인 구분",
                            examples = [
                                ExampleObject(name = "kakao", value = "kakao", description = "카카오 로그인")
                            ]
                        ),
                        Parameter(
                            name = "code",
                            description = "인가코드",
                            example = "0"
                        )
                    ],
                    responses = [
                        ApiResponse(
                            description = "획득한 토큰정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = ResultTokenInfo::class))]
                        )
                    ]
                )
            ),
            RouterOperation(
                path = "/auth/refresh",
                method = [RequestMethod.GET],
                beanClass = AuthHandler::class,
                beanMethod = "refresh",
                operation = Operation(
                    tags = ["인증관리"],
                    summary = "토큰 재발급",
                    operationId = "refresh",
                    parameters = [
                        Parameter(
                            name = "refresh_token",
                            description = "리프레시 토큰",
                            examples = [
                                ExampleObject(name = "refresh_token", value = "TEST_REFRESH_TOKEN", description = "리프레시 토큰")
                            ]
                        )
                    ],
                    responses = [
                        ApiResponse(
                            description = "획득한 토큰정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = ResultTokenInfo::class))]
                        )
                    ]
                )
            )
        ]
    )
    fun authRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/auth/authorize", authHandler::authorize)
            GET("/auth/token", authHandler::token)
            GET("/auth/refresh", authHandler::refresh)
        }
    }
}