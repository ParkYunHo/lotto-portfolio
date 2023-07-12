package com.john.lotto.number.adapter.`in`.web

import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.number.application.port.`in`.FindLottoNumberUseCase
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

    /**
     * 특정 로또번호 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findLottoNumber(request: ServerRequest): Mono<ServerResponse> =
        lottoNumberUseCase.findLottoNumber(
            request.queryParam("drwtNo").orElseThrow { BadRequestException("필수 입력값 누락") }.toLong()
        )
            .flatMap { BaseResponse().success(it) }

    /**
     * 최신 로또번호 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findLottoNumberLatest(request: ServerRequest): Mono<ServerResponse> =
        lottoNumberUseCase.findLottoNumberLatest()
            .flatMap { BaseResponse().success(it) }
}