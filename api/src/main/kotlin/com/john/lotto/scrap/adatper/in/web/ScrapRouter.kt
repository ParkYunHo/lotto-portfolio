package com.john.lotto.scrap.adatper.`in`.web

import com.john.lotto.scrap.adatper.`in`.web.dto.ScrapInput
import com.john.lotto.scrap.dto.StoreScrapDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
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
 * @since 2023.07.19
 */
@Configuration
class ScrapRouter(
    private val scrapHandler: ScrapHandler
) {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/scrap/store",
                method = [RequestMethod.POST],
                beanClass = ScrapHandler::class,
                beanMethod = "register",
                operation = Operation(
                    tags = ["판매점스크랩"],
                    summary = "판매점스크랩 등록",
                    operationId = "register",
                    requestBody = RequestBody(
                        required = true,
                        description = "판매점스크랩 등록정보",
                        content = [
                            Content(
                                schema = Schema(implementation = ScrapInput::class)
                            )
                        ]
                    ),
                    responses = [
                        ApiResponse(
                            description = "등록된 판매점스크랩정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = StoreScrapDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/scrap/store",
                method = [RequestMethod.GET],
                beanClass = ScrapHandler::class,
                beanMethod = "search",
                operation = Operation(
                    tags = ["판매점스크랩"],
                    summary = "판매점스크랩 조회",
                    operationId = "search",
                    responses = [
                        ApiResponse(
                            description = "판매점스크랩 정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = StoreScrapDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/scrap/store",
                method = [RequestMethod.DELETE],
                beanClass = ScrapHandler::class,
                beanMethod = "delete",
                operation = Operation(
                    tags = ["판매점스크랩"],
                    summary = "판매점스크랩 삭제",
                    operationId = "delete",
                    responses = [
                        ApiResponse(
                            description = "삭제된 회원ID",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = String::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
        ]
    )
    fun scrapRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/api/scrap/store", scrapHandler::register)
            GET("/api/scrap/store", scrapHandler::search)
            DELETE("/api/scrap/store", scrapHandler::delete)
        }
    }
}