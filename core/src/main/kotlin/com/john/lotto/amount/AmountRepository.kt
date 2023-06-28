package com.john.lotto.amount

import com.john.lotto.entity.QLottoWinAmount
import com.john.lotto.amount.dto.LottoWinAmountDto
import com.john.lotto.amount.dto.QLottoWinAmountDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.06.28
 */
@Repository
class AmountRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val lottoWinAmount = QLottoWinAmount.lottoWinAmount!!


    /**
     * 로또당첨금 조회
     *
     * @param drwtNo [Long]
     * @return [LottoWinAmountDto]?
     * @author yoonho
     * @since 2023.06.28
     */
    @Transactional(readOnly = true)
    fun findLottoWinAmount(drwtNo: Long): LottoWinAmountDto? =
        queryFactory
            .select(
                QLottoWinAmountDto(
                    lottoWinAmount.drwtNo,
                    lottoWinAmount.drwtDate,
                    lottoWinAmount.totSellamnt,
                    lottoWinAmount.firstWinamnt,
                    lottoWinAmount.firstPrzwnerCo,
                    lottoWinAmount.firstAccumamnt
                )
            )
            .from(lottoWinAmount)
            .where(
                lottoWinAmount.drwtNo.eq(drwtNo)
            )
            .fetchFirst()

    /**
     * 로또 당첨금 저장
     *
     * @param input [LottoWinAmountDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.06.28
     */
    @Transactional
    fun insertLottoWinAmount(input: LottoWinAmountDto): Long =
        queryFactory
            .insert(lottoWinAmount)
            .columns(
                lottoWinAmount.drwtNo,
                lottoWinAmount.drwtDate,
                lottoWinAmount.totSellamnt,
                lottoWinAmount.firstWinamnt,
                lottoWinAmount.firstPrzwnerCo,
                lottoWinAmount.firstAccumamnt,
            )
            .values(
                input.drwtNo,
                input.drwtDate,
                input.totSellamnt,
                input.firstWinamnt,
                input.firstPrzwnerCo,
                input.firstAccumamnt,
            )
            .execute()
}