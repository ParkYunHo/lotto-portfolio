package com.john.lotto.store.adapter.`in`.web

import com.john.lotto.common.constants.CommCode
import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.filter.userId
import com.john.lotto.store.application.port.`in`.FindLocationUseCase
import com.john.lotto.store.application.port.`in`.FindStoreUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import kotlin.jvm.optionals.getOrDefault

/**
 * @author yoonho
 * @since 2023.07.18
 */
@Component
class StoreHandler(
    private val findStoreUseCase: FindStoreUseCase,
    private val findLocationUseCase: FindLocationUseCase
) {

    /**
     * 로또 판매점 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.18
     */
    fun findStore(request: ServerRequest): Mono<ServerResponse> =
        findStoreUseCase.findStore(
            userId = request.userId(),
            location = request.queryParam("location").orElseThrow { BadRequestException("필수 입력값 누락") }.toString(),
            subLocation = request.queryParam("subLocation").orElseThrow { BadRequestException("필수 입력값 누락") }.toString(),
            sort = request.queryParam("sort").orElseGet { CommCode.Sort.NAME.code },
            option = request.queryParam("option").orElseGet { CommCode.Option.ALL.code }
        )
            .collectList()
            .flatMap { BaseResponse().success(it) }

    /**
     * 판매점 위치정보 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.18
     */
    fun findLocation(request: ServerRequest): Mono<ServerResponse> =
        findLocationUseCase.findLocation()
            .collectList()
            .flatMap { BaseResponse().success(it) }
}
