package com.john.lotto.statics.adapter.`in`.web

import com.john.lotto.statics.application.dto.StaticsInfo
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
 * @since 2023.07.12
 */
@Configuration
class StaticsRouter(
    private val staticsHandler: StaticsHandler
) {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/statics/period",
                method = [RequestMethod.GET],
                beanClass = StaticsHandler::class,
                beanMethod = "findPeriod",
                operation = Operation(
                    tags = ["당첨번호 통계"],
                    summary = "월별/연도별 당첨번호 통계",
                    operationId = "findPeriod",
                    parameters = [
                        Parameter(
                            name = "startDt",
                            description = "시작날짜(yyyyMM)",
                            required = true,
                            examples = [
                                ExampleObject(name = "202306", value = "202306", description = "yyyyMM")
                            ]
                        ),
                        Parameter(
                            name = "endDt",
                            description = "종료날짜(yyyyMM)",
                            required = true,
                            examples = [
                                ExampleObject(name = "202307", value = "202307", description = "yyyyMM")
                            ]
                        ),
                        Parameter(
                            name = "sortType",
                            description = "정렬구분",
                            required = true,
                            examples = [
                                ExampleObject(name = "NO", value = "NO", description = "로또번호 정렬"),
                                ExampleObject(name = "COUNT", value = "COUNT", description = "통계카운트 정렬"),
                            ]
                        ),
                        Parameter(
                            name = "sortOption",
                            description = "정렬옵션",
                            required = true,
                            examples = [
                                ExampleObject(name = "DESC", value = "DESC", description = "내림차순(desc)"),
                                ExampleObject(name = "ASC", value = "ASC", description = "오름차순(asc)"),
                            ]
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            description = "당첨번호 통계",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = StaticsInfo::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/statics/number",
                method = [RequestMethod.GET],
                beanClass = StaticsHandler::class,
                beanMethod = "findDrwtNo",
                operation = Operation(
                    tags = ["당첨번호 통계"],
                    summary = "회차별 당첨번호 통계",
                    operationId = "findDrwtNo",
                    parameters = [
                        Parameter(
                            name = "startNo",
                            description = "시작 회차",
                            required = true,
                            examples = [
                                ExampleObject(name = "1060", value = "1060", description = "시작 회차")
                            ]
                        ),
                        Parameter(
                            name = "endNo",
                            description = "종료 회차",
                            required = true,
                            examples = [
                                ExampleObject(name = "1070", value = "1070", description = "종료 회차")
                            ]
                        ),
                        Parameter(
                            name = "sortType",
                            description = "정렬구분",
                            required = true,
                            examples = [
                                ExampleObject(name = "NO", value = "NO", description = "로또번호 정렬"),
                                ExampleObject(name = "COUNT", value = "COUNT", description = "통계카운트 정렬"),
                            ]
                        ),
                        Parameter(
                            name = "sortOption",
                            description = "정렬옵션",
                            required = true,
                            examples = [
                                ExampleObject(name = "DESC", value = "DESC", description = "내림차순(desc)"),
                                ExampleObject(name = "ASC", value = "ASC", description = "오름차순(asc)"),
                            ]
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            description = "당첨번호 통계",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = StaticsInfo::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/statics/rank",
                method = [RequestMethod.GET],
                beanClass = StaticsHandler::class,
                beanMethod = "findWinAmount",
                operation = Operation(
                    tags = ["당첨번호 통계"],
                    summary = "당첨금액별 당첨번호 통계",
                    operationId = "findWinAmount",
                    parameters = [
                        Parameter(
                            name = "startRank",
                            description = "시작 등수",
                            required = true,
                            examples = [
                                ExampleObject(name = "0", value = "0", description = "시작 등수")
                            ]
                        ),
                        Parameter(
                            name = "size",
                            description = "조회할 개수",
                            required = true,
                            examples = [
                                ExampleObject(name = "10", value = "10", description = "조회할 개수")
                            ]
                        ),
                        Parameter(
                            name = "rankSortOption",
                            description = "랭크 정렬옵션",
                            required = true,
                            examples = [
                                ExampleObject(name = "DESC", value = "DESC", description = "내림차순(desc)"),
                                ExampleObject(name = "ASC", value = "ASC", description = "오름차순(asc)"),
                            ]
                        ),
                        Parameter(
                            name = "sortType",
                            description = "정렬구분",
                            required = true,
                            examples = [
                                ExampleObject(name = "NO", value = "NO", description = "로또번호 정렬"),
                                ExampleObject(name = "COUNT", value = "COUNT", description = "통계카운트 정렬"),
                            ]
                        ),
                        Parameter(
                            name = "sortOption",
                            description = "정렬옵션",
                            required = true,
                            examples = [
                                ExampleObject(name = "DESC", value = "DESC", description = "내림차순(desc)"),
                                ExampleObject(name = "ASC", value = "ASC", description = "오름차순(asc)"),
                            ]
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            description = "당첨번호 통계",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = StaticsInfo::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
        ]
    )
    fun staticsRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/api/statics/period", staticsHandler::findPeriod)
            GET("/api/statics/number", staticsHandler::findDrwtNo)
            GET("/api/statics/rank", staticsHandler::findWinAmount)
        }
    }
}