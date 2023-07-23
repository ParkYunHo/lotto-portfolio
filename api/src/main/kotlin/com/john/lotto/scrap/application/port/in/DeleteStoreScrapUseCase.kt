package com.john.lotto.scrap.application.port.`in`

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
     * @param rtlrid [String]
     * @return [Mono]<[String]>
     * @author yoonho
     * @since 2023.07.23
     */
    fun delete(userId: String, rtlrid: String): Mono<String>
}