package com.john.lotto.statics

import com.john.lotto.entity.QLottoNumber
import com.john.lotto.entity.QLottoWinAmount
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Repository
class StaticsRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val lottoNumber = QLottoNumber.lottoNumber!!
    private val lottoWinAmount = QLottoWinAmount.lottoWinAmount!!

    /**
     * 월별/연도별 당첨번호 통계
     *
     * @param startDt [LocalDate]
     * @param endDt [LocalDate]
     * @return [Map]<[Long], [Long]>
     * @author yoonho
     * @since 2023.07.07
     */
    @Transactional(readOnly = true)
    fun findPeriodStatics(startDt: LocalDate, endDt: LocalDate): Map<Long, Long> {
        val result = mutableMapOf<Long, Long>()
        for(idx: Long in 1L..45L) {
            result[idx] = queryFactory
                .select(lottoNumber.drwtNo.count())
                .from(lottoNumber)
                .where(
                    // 기간별 조회
                    lottoNumber.drwtDate.between(startDt, endDt),
                    // 1-45번 개수 조회
                    this.eqLottoNumberDrwtNo(idx)
                )
                .fetchFirst()
        }
        return result
    }

    /**
     * 회차별 당첨번호 통계
     *
     * @param startDrwtNo [Long]
     * @param endDrwtNo [Long]
     * @return [Map]<[Long], [Long]>
     * @author yoonho
     * @since 2023.07.07
     */
    @Transactional(readOnly = true)
    fun findDrwtNoStatics(startDrwtNo: Long, endDrwtNo: Long): Map<Long, Long> {
        val result = mutableMapOf<Long, Long>()
        for(idx: Long in 1L..45L) {
            result[idx] = queryFactory
                .select(lottoNumber.drwtNo.count())
                .from(lottoNumber)
                .where(
                    // 회차별 조회
                    lottoNumber.drwtNo.between(startDrwtNo, endDrwtNo),
                    // 1-45번 개수 조회
                    this.eqLottoNumberDrwtNo(idx)
                )
                .fetchFirst()
        }
        return result
    }

    /**
     * 당첨금액별 당첨번호 통계
     *
     * @param startRank [Long]
     * @param size [Long]
     * @param isDesc [Boolean]
     * @return [Map]<[Long], [Long]>
     * @author yoonho
     * @since 2023.07.07
     */
    @Transactional(readOnly = true)
    fun findWinAmountStatics(startRank: Long, size: Long, isDesc: Boolean): Map<Long, Long> {
        val result = mutableMapOf<Long, Long>()

        // 1인당 당첨금액 정렬
        val drwtNos = queryFactory
            .select(lottoWinAmount.drwtNo)
            .from(lottoWinAmount)
            .orderBy(
                if(isDesc) lottoWinAmount.firstWinamnt.desc()
                else lottoWinAmount.firstWinamnt.asc()
            )
            .offset(startRank)
            .limit(size)
            .fetch()

        for(idx: Long in 1L..45L) {
            result[idx] = queryFactory
                .select(lottoNumber.drwtNo.count())
                .from(lottoNumber)
                .where(
                    // 회차별 조회
                    lottoNumber.drwtNo.`in`(drwtNos),
                    // 1-45번 개수 조회
                    this.eqLottoNumberDrwtNo(idx)
                )
                .fetchFirst()
        }
        return result
    }

    /**
     * 1-45번 당첨번호 조회
     *
     * @param idx [Long]
     * @return [BooleanExpression]
     * @author yoonho
     * @since 2023.07.07
     */
    private fun eqLottoNumberDrwtNo(idx: Long): BooleanExpression =
        // 1-45번 개수 조회
        lottoNumber.drwtNo1.eq(idx).or(
        lottoNumber.drwtNo2.eq(idx).or(
        lottoNumber.drwtNo3.eq(idx).or(
        lottoNumber.drwtNo4.eq(idx).or(
        lottoNumber.drwtNo5.eq(idx).or(
        lottoNumber.drwtNo6.eq(idx).or(
        lottoNumber.bnusNo.eq(idx)))))))
}