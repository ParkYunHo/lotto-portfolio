package com.john.lotto.number.adapter.`in`.web

import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.number.application.port.`in`.FindLottoNumberUseCase
import feign.FeignException.BadRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


/**
 * @author yoonho
 * @since 2023.06.22
 */
@Component
class NumberHandler(
    private val lottoNumberUseCase: FindLottoNumberUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun findLottoNumber(request: ServerRequest): Mono<ServerResponse> =
        lottoNumberUseCase.findLottoNumber(
            request.queryParam("drwtNo").orElseThrow { BadRequestException("필수 입력값 누락") }
                .toLong()
        )
            .flatMap { BaseResponse().success(it) }

}