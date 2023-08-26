package com.john.lotto.member.adapter.`in`.web

import com.john.lotto.member.adapter.`in`.web.dto.MemberInput
import com.john.lotto.member.dto.MemberDto
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
class MemberRouter(
    private val memberHandler: MemberHandler
) {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/member",
                method = [RequestMethod.POST],
                beanClass = MemberHandler::class,
                beanMethod = "register",
                operation = Operation(
                    tags = ["회원정보"],
                    summary = "회원정보 등록",
                    operationId = "register",
                    requestBody = RequestBody(
                        required = true,
                        description = "회원 등록정보",
                        content = [
                            Content(
                                schema = Schema(implementation = MemberInput::class)
                            )
                        ]
                    ),
                    responses = [
                        ApiResponse(
                            description = "등록된 회원정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = MemberDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/member",
                method = [RequestMethod.PATCH],
                beanClass = MemberHandler::class,
                beanMethod = "update",
                operation = Operation(
                    tags = ["회원정보"],
                    summary = "회원정보 수정",
                    operationId = "update",
                    requestBody = RequestBody(
                        required = true,
                        description = "회원 수정정보",
                        content = [
                            Content(
                                schema = Schema(implementation = MemberInput::class)
                            )
                        ]
                    ),
                    responses = [
                        ApiResponse(
                            description = "수정된 회원정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = MemberDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/member",
                method = [RequestMethod.GET],
                beanClass = MemberHandler::class,
                beanMethod = "search",
                operation = Operation(
                    tags = ["회원정보"],
                    summary = "회원정보 조회",
                    operationId = "search",
                    responses = [
                        ApiResponse(
                            description = "회원정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = MemberDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "OpenID Connection Authentication")]
                )
            ),
            RouterOperation(
                path = "/api/member",
                method = [RequestMethod.DELETE],
                beanClass = MemberHandler::class,
                beanMethod = "delete",
                operation = Operation(
                    tags = ["회원정보"],
                    summary = "회원정보 삭제",
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
    fun memberRouterFunction(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/api/member", memberHandler::register)
            PATCH("/api/member", memberHandler::update)
            GET("/api/member", memberHandler::search)
            DELETE("/api/member", memberHandler::delete)
        }
    }
}