package com.john.lotto.number.application

import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.number.NumberRepository
import com.john.lotto.number.application.port.`in`.FindLottoNumberUseCase
import com.john.lotto.number.dto.LottoNumberDto
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
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
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 특정 로또번호 조회
     *
     * @param drwtNo [Long]
     * @return [Mono]<[LottoNumberDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    @Cacheable(cacheNames = ["number.one"], key = "#drwtNo", unless = "#result == null")
    override fun findLottoNumber(drwtNo: Long): Mono<LottoNumberDto> {
        val result = numberRepository.findLottoNumber(drwtNo = drwtNo) ?: throw BadRequestException("로또번호가 존재하지 않습니다.")

        log.info(" >>> [findLottoNumber] drwtNo: $drwtNo, result: $result")
        return Mono.just(result)
    }


    /**
     * 최신 로또번호 조회
     *
     * @return [Mono]<[LottoNumberDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    @Cacheable(cacheNames = ["number.latest"], unless = "#result == null")
    override fun findLottoNumberLatest(): Mono<LottoNumberDto> {
        val result = numberRepository.findLottoNumberLatest() ?: throw InternalServerException("데이터 조회에 실패하였습니다.")

        log.info(" >>> [findLottoNumberLatest] result: $result")
        return Mono.just(result)
    }
}