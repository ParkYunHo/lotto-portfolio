package com.john.lotto.number.application.port.`in`

import com.john.lotto.number.dto.LottoNumberDto
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
     * @return [Mono]<[LottoNumberDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findLottoNumber(drwtNo: Long): Mono<LottoNumberDto>

    /**
     * 최신 로또번호 조회
     *
     * @return [Mono]<[LottoNumberDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findLottoNumberLatest(): Mono<LottoNumberDto>
}