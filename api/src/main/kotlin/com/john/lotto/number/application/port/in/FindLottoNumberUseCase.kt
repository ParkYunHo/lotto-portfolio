package com.john.lotto.number.application.port.`in`

import com.john.lotto.number.application.dto.LottoTotalInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.06.22
 */
interface FindLottoNumberUseCase {

    /**
     * 특정 로또번호 조회
     *
     * @param drwtNo [Long]
     * @return [Mono]<[LottoTotalInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findLottoNumber(drwtNo: Long): Mono<LottoTotalInfo>

    /**
     * 최신 로또번호 조회
     *
     * @return [Mono]<[LottoTotalInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findLottoNumberLatest(): Mono<LottoTotalInfo>
}