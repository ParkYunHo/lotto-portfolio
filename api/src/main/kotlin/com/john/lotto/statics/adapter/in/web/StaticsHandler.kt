package com.john.lotto.statics.adapter.`in`.web

import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.statics.application.port.`in`.StaticsUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.12
 */
@Component
class StaticsHandler(
    private val staticsUseCase: StaticsUseCase
) {

    /**
     * 월별/연도별 당첨번호 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findPeriod(request: ServerRequest): Mono<ServerResponse> =
        staticsUseCase.findPeriod(
            startDtStr = request.queryParam("startDt").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            endDtStr = request.queryParam("endDt").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            sortOption = request.queryParam("sortOption").orElseThrow { BadRequestException("필수 입력값 누락") }.trim()
        )
            .collectList()
            .flatMap { BaseResponse().success(it) }

    /**
     * 회차별 당첨번호 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findDrwtNo(request: ServerRequest): Mono<ServerResponse> =
        staticsUseCase.findDrwtNo(
            startDrwtNo = request.queryParam("startNo").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            endDrwtNo = request.queryParam("endNo").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            sortOption = request.queryParam("sortOption").orElseThrow { BadRequestException("필수 입력값 누락") }.trim()
        )
            .collectList()
            .flatMap { BaseResponse().success(it) }

    /**
     * 당첨금액별 당첨번호 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findWinAmount(request: ServerRequest): Mono<ServerResponse> =
        staticsUseCase.findWinAmount(
            startRank = request.queryParam("startRank").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            size = request.queryParam("size").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            isDesc = request.queryParam("isDesc").orElseThrow { BadRequestException("필수 입력값 누락") }.trim(),
            sortOption = request.queryParam("sortOption").orElseThrow { BadRequestException("필수 입력값 누락") }.trim()
        )
            .collectList()
            .flatMap { BaseResponse().success(it) }
}