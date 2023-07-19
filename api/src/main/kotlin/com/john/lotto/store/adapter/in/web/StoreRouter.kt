package com.john.lotto.store.adapter.`in`.web

import com.john.lotto.store.application.dto.LottoStoreTotalInfo
import com.john.lotto.store.dto.LocationDto
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
 * @since 2023.07.18
 */
@Configuration
class StoreRouter(
    private val storeHandler: StoreHandler
) {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/location",
                method = [RequestMethod.GET],
                beanClass = StoreHandler::class,
                beanMethod = "findLocation",
                operation = Operation(
                    tags = ["로또판매점 조회"],
                    summary = "판매점 위치정보 조회",
                    operationId = "findLocation",
                    responses = [
                        ApiResponse(
                            description = "위치정",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = LocationDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/store",
                method = [RequestMethod.GET],
                beanClass = StoreHandler::class,
                beanMethod = "findStore",
                operation = Operation(
                    tags = ["로또판매점 조회"],
                    summary = "로또판매점 리스트 조회",
                    operationId = "findStore",
                    parameters = [
                        Parameter(
                            name = "location",
                            description = "조회할 판매점 위치(시)",
                            required = true,
                            examples = [
                                ExampleObject(name = "서울", value = "서울", description = "조회할 판매점 위치(시)")
                            ]
                        ),
                        Parameter(
                            name = "subLocation",
                            description = "조회할 판매점 위치(구)",
                            required = true,
                            examples = [
                                ExampleObject(name = "서초구", value = "서초구", description = "조회할 판매점 위치(구)")
                            ]
                        ),
                        Parameter(
                            name = "sort",
                            description = "정렬",
                            required = true,
                            examples = [
                                ExampleObject(name = "판매점이름", value = "0", description = "판매점이름 오름차순"),
                                ExampleObject(name = "1등당첨금액", value = "1", description = "1등당첨금액"),
                                ExampleObject(name = "최신회차 당첨", value = "2", description = "최신회차 당첨")
                            ]
                        ),
                        Parameter(
                            name = "option",
                            description = "노출판매점 옵션(전체, 명당, 판매점)",
                            required = true,
                            examples = [
                                ExampleObject(name = "전체", value = "0", description = "명당과 판매점 모두 조회"),
                                ExampleObject(name = "명당", value = "1", description = "명당만 조회"),
                                ExampleObject(name = "판매점", value = "2", description = "명당이 제외된 판매점 조회")
                            ]
                        )
                    ],
                    responses = [
                        ApiResponse(
                            description = "로또판매점 리스트",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = LottoStoreTotalInfo::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
        ]
    )
    fun mapRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/api/location", storeHandler::findLocation)
            GET("/api/store", storeHandler::findStore)
        }
    }
}