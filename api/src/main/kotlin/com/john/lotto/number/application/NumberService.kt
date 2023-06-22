package com.john.lotto.number.application

import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.number.NumberRepository
import com.john.lotto.number.application.port.`in`.FindLottoNumberUseCase
import com.john.lotto.number.dto.LottoNumberDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.06.22
 */
@Service
class NumberService(
    private val numberRepository: NumberRepository
): FindLottoNumberUseCase {

    override fun findLottoNumber(drwtNo: Long): Mono<LottoNumberDto> =
        Mono.just(numberRepository.findLottoNumber(drwtNo = drwtNo) ?: throw BadRequestException("로또번호가 존재하지 않습니다."))
}