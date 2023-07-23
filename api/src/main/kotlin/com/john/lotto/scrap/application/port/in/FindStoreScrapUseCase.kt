package com.john.lotto.scrap.application.port.`in`

import com.john.lotto.scrap.dto.StoreScrapDto
import reactor.core.publisher.Flux

/**
 * @author yoonho
 * @since 2023.07.23
 */
interface FindStoreScrapUseCase {

    /**
     * 판매점스크랩 조회
     *
     * @param userId [String]
     * @return [Flux]<[StoreScrapDto]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun search(userId: String): Flux<StoreScrapDto>
}