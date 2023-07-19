package com.john.lotto.store.application.port.`in`

import com.john.lotto.store.application.dto.LottoStoreTotalInfo
import reactor.core.publisher.Flux

/**
 * @author yoonho
 * @since 2023.07.18
 */
interface FindStoreUseCase {

    /**
     * 로또 판매점 조회
     *
     * @param location [String]
     * @param subLocation [String]
     * @param sort [String]
     * @param option [String]
     * @return [Flux]<[LottoStoreTotalInfo]>
     * @author yoonho
     * @since 2023.07.18
     */
    fun findStore(location: String, subLocation: String, sort: String, option: String): Flux<LottoStoreTotalInfo>
}