package com.john.lotto.scrap.adatper.`in`.web

import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.filter.userId
import com.john.lotto.scrap.adatper.`in`.web.dto.ScrapInput
import com.john.lotto.scrap.application.port.`in`.DeleteStoreScrapUseCase
import com.john.lotto.scrap.application.port.`in`.FindStoreScrapUseCase
import com.john.lotto.scrap.application.port.`in`.RegisterScrapUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.19
 */
@Component
class ScrapHandler(
    private val registerScrapUseCase: RegisterScrapUseCase,
    private val findStoreScrapUseCase: FindStoreScrapUseCase,
    private val deleteStoreScrapUseCase: DeleteStoreScrapUseCase
) {

    /**
     * 판매점스크랩 등록
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun register(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ScrapInput::class.java)
            .flatMap { return@flatMap Mono.just(it.validate()) }
            .flatMap { registerScrapUseCase.register(userId = request.userId(), rtlrid = it.rtlrid!!) }
            .flatMap { BaseResponse().success(it) }

    /**
     * 판매점스크랩 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun search(request: ServerRequest): Mono<ServerResponse> =
        findStoreScrapUseCase.search(userId = request.userId())
            .collectList()
            .flatMap { BaseResponse().success(it) }

    /**
     * 판매점스크랩 탈퇴
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun delete(request: ServerRequest): Mono<ServerResponse> =
        deleteStoreScrapUseCase.delete(
            userId = request.userId(),
            rtlrid = request.queryParam("rtlrid").orElseThrow { BadRequestException("필수 입력값 누락") }.toString()
        )
            .flatMap { BaseResponse().success(it) }
}