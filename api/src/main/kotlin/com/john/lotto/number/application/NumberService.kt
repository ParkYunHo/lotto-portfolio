package com.john.lotto.number.application

import com.john.lotto.amount.AmountRepository
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.number.NumberRepository
import com.john.lotto.number.application.dto.LottoTotalInfo
import com.john.lotto.number.application.port.`in`.FindLottoNumberUseCase
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
    private val numberRepository: NumberRepository,
    private val amountRepository: AmountRepository
): FindLottoNumberUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 특정 로또번호 조회
     *
     * @param drwtNo [Long]
     * @return [Mono]<[LottoTotalInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    @Cacheable(cacheNames = ["number.one"], key = "#drwtNo", unless = "#result == null")
    override fun findLottoNumber(drwtNo: Long): Mono<LottoTotalInfo> {
        val lottoNumber = numberRepository.findLottoNumber(drwtNo = drwtNo) ?: throw BadRequestException("로또번호가 존재하지 않습니다.")
        val lottoWinAmount = amountRepository.findLottoWinAmount(drwtNo = drwtNo) ?: throw BadRequestException("로또번호가 존재하지 않습니다.")

        val result = LottoTotalInfo(
            drwtNo = lottoNumber.drwtNo,
            drwtDate = lottoNumber.drwtDate,
            drwtNo1 = lottoNumber.drwtNo1,
            drwtNo2 = lottoNumber.drwtNo2,
            drwtNo3 = lottoNumber.drwtNo3,
            drwtNo4 = lottoNumber.drwtNo4,
            drwtNo5 = lottoNumber.drwtNo5,
            drwtNo6 = lottoNumber.drwtNo6,
            bnusNo = lottoNumber.bnusNo,

            totSellAmount = lottoWinAmount.totSellamnt,
            firstWinAmount = lottoWinAmount.firstWinamnt,
            firstWinCount = lottoWinAmount.firstPrzwnerCo,
            firstTotAmount = lottoWinAmount.firstAccumamnt,
        )
        log.info(" >>> [findLottoNumber] drwtNo: $drwtNo, result: $result")
        return Mono.just(result)
    }


    /**
     * 최신 로또번호 조회
     *
     * @return [Mono]<[LottoTotalInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    @Cacheable(cacheNames = ["number.latest"], unless = "#result == null")
    override fun findLottoNumberLatest(): Mono<LottoTotalInfo> {
        val lottoNumber = numberRepository.findLottoNumberLatest() ?: throw InternalServerException("데이터 조회에 실패하였습니다.")
        val lottoWinAmount = amountRepository.findLottoWinAmount(drwtNo = lottoNumber.drwtNo!!) ?: throw BadRequestException("데이터 조회에 실패하였습니다.")

        val result = LottoTotalInfo(
            drwtNo = lottoNumber.drwtNo,
            drwtDate = lottoNumber.drwtDate,
            drwtNo1 = lottoNumber.drwtNo1,
            drwtNo2 = lottoNumber.drwtNo2,
            drwtNo3 = lottoNumber.drwtNo3,
            drwtNo4 = lottoNumber.drwtNo4,
            drwtNo5 = lottoNumber.drwtNo5,
            drwtNo6 = lottoNumber.drwtNo6,
            bnusNo = lottoNumber.bnusNo,

            totSellAmount = lottoWinAmount.totSellamnt,
            firstWinAmount = lottoWinAmount.firstWinamnt,
            firstWinCount = lottoWinAmount.firstPrzwnerCo,
            firstTotAmount = lottoWinAmount.firstAccumamnt,
        )
        log.info(" >>> [findLottoNumberLatest] result: $result")
        return Mono.just(result)
    }
}