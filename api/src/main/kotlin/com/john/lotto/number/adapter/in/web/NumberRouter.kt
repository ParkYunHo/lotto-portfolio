package com.john.lotto.number.adapter.`in`.web

import com.john.lotto.number.dto.LottoNumberDto
import com.john.lotto.statics.dto.StaticsDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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
 * @since 2023.06.22
 */
@Configuration
class NumberRouter(
    private val numberHandler: NumberHandler
) {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/number",
                method = [RequestMethod.GET],
                beanClass = NumberHandler::class,
                beanMethod = "findLottoNumber",
                operation = Operation(
                    tags = ["당첨번호 조회"],
                    summary = "특정 당첨번호 조회",
                    operationId = "findLottoNumber",
                    parameters = [
                        Parameter(
                            name = "drwtNo",
                            description = "조회할 로또회차",
                            required = true,
                            examples = [
                                ExampleObject(name = "1070", value = "1070", description = "조회할 로또회차")
                            ]
                        )
                    ],
                    responses = [
                        ApiResponse(
                            description = "당첨번호",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = LottoNumberDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/number/latest",
                method = [RequestMethod.GET],
                beanClass = NumberHandler::class,
                beanMethod = "findLottoNumberLatest",
                operation = Operation(
                    tags = ["당첨번호 조회"],
                    summary = "최신 당첨번호 조회",
                    operationId = "findLottoNumberLatest",
                    responses = [
                        ApiResponse(
                            description = "당첨번호",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = LottoNumberDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
        ]
    )
    fun numberRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/api/number", numberHandler::findLottoNumber)
            GET("/api/number/latest", numberHandler::findLottoNumberLatest)
        }
    }
}