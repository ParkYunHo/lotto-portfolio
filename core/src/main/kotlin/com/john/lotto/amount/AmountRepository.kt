package com.john.lotto.amount

import com.john.lotto.amount.dto.LottoWinAmountDto
import com.john.lotto.amount.dto.QLottoWinAmountDto
import com.john.lotto.entity.lotto.QLottoWinAmount
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.28
 */
@Repository
class AmountRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
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
                    lottoWinAmount.firstAccumamnt,
                    lottoWinAmount.updatedAt,
                    lottoWinAmount.createdAt
                )
            )
            .from(lottoWinAmount)
            .where(
                lottoWinAmount.drwtNo.eq(drwtNo)
            )
            .fetchFirst()

    /**
     * 로또당첨금 조회
     *
     * @param drwtNos [List]<[Long]>
     * @return [List]<[LottoWinAmountDto]>
     * @author yoonho
     * @since 2023.07.19
     */
    @Transactional(readOnly = true)
    fun findLottoWinAmountList(drwtNos: List<Long>): List<LottoWinAmountDto> =
        queryFactory
            .select(
                QLottoWinAmountDto(
                    lottoWinAmount.drwtNo,
                    lottoWinAmount.drwtDate,
                    lottoWinAmount.totSellamnt,
                    lottoWinAmount.firstWinamnt,
                    lottoWinAmount.firstPrzwnerCo,
                    lottoWinAmount.firstAccumamnt,
                    lottoWinAmount.updatedAt,
                    lottoWinAmount.createdAt
                )
            )
            .from(lottoWinAmount)
            .where(
                this.inLottoWinAmountDrwtNos(drwtNos = drwtNos)
            )
            .fetch()

    private fun inLottoWinAmountDrwtNos(drwtNos: List<Long>): BooleanExpression? =
        if(drwtNos.isEmpty()) null
        else lottoWinAmount.drwtNo.`in`(drwtNos)

    /**
     * 당첨금액별 당첨금액정보
     *
     * @param size [Long]
     * @param isDesc [Boolean]
     * @return [List]<[LottoWinAmountDto]>
     * @author yoonho
     * @since 2023.09.06
     */
    @Transactional(readOnly = true)
    fun findLottoWinAmountSort(size: Long, isDesc: Boolean): List<LottoWinAmountDto> =
        queryFactory
            .select(
                QLottoWinAmountDto(
                    lottoWinAmount.drwtNo,
                    lottoWinAmount.drwtDate,
                    lottoWinAmount.totSellamnt,
                    lottoWinAmount.firstWinamnt,
                    lottoWinAmount.firstPrzwnerCo,
                    lottoWinAmount.firstAccumamnt,
                    lottoWinAmount.updatedAt,
                    lottoWinAmount.createdAt
                )
            )
            .from(lottoWinAmount)
            .orderBy(
                if(isDesc) lottoWinAmount.firstWinamnt.desc()
                else lottoWinAmount.firstWinamnt.asc()
            )
            .limit(size)
            .fetch()

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
                lottoWinAmount.updatedAt,
                lottoWinAmount.createdAt
            )
            .values(
                input.drwtNo,
                input.drwtDate,
                input.totSellamnt,
                input.firstWinamnt,
                input.firstPrzwnerCo,
                input.firstAccumamnt,
                null,
                LocalDateTime.now()
            )
            .execute()
}