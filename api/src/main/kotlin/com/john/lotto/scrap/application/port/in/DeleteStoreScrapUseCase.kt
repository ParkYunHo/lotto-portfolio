package com.john.lotto.scrap.application.port.`in`

import com.john.lotto.scrap.adatper.`in`.web.dto.ScrapResult
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.23
 */
interface DeleteStoreScrapUseCase {

    /**
     * 판매점스크랩 탈퇴
     *
     * @param userId [String]
     * @param storeId [String]
     * @return [Mono]<[ScrapResult]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun delete(userId: String, storeId: String): Mono<ScrapResult>
}