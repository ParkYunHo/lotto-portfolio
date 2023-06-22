package com.john.lotto.number.application.port.`in`

import com.john.lotto.number.dto.LottoNumberDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.06.22
 */
interface FindLottoNumberUseCase {
    fun findLottoNumber(drwtNo: Long): Mono<LottoNumberDto>
}