package com.john.lotto.auth.adapter.`in`.web

import com.john.lotto.auth.application.port.`in`.AuthorizeUseCase
import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Component
class AuthHandler(
    private val authorizeUseCase: AuthorizeUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun authorize(request: ServerRequest): Mono<ServerResponse> =
        authorizeUseCase.authorize()
            .flatMap { ServerResponse.temporaryRedirect(URI(it)).build() }

    fun token(request: ServerRequest): Mono<ServerResponse> =
        authorizeUseCase.token(
            state = request.queryParam("state").orElseThrow { throw BadRequestException("state 정보가 누락되었습니다.") },
            code = request.queryParam("code").orElseThrow { throw BadRequestException("code 정보가 누락되었습니다.") }
        )
            .flatMap { BaseResponse().success(it) }
}