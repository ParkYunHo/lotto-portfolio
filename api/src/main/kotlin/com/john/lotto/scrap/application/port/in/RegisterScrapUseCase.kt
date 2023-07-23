package com.john.lotto.scrap.application.port.`in`

import com.john.lotto.scrap.dto.StoreScrapDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.23
 */
interface RegisterScrapUseCase {

    /**
     * 판매점스크랩 등록
     *
     * @param userId [String]
     * @param rtlrid [String]
     * @return [Mono]<[StoreScrapDto]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun register(userId: String, rtlrid: String): Mono<StoreScrapDto>
}