package com.john.lotto.number

import com.john.lotto.entity.QLottoNumber
import com.john.lotto.number.dto.LottoNumberDto
import com.john.lotto.number.dto.QLottoNumberDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Repository
class NumberRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val lottoNumber = QLottoNumber.lottoNumber!!


    /**
     * 로또번호 조회
     *
     * @param drwtNo [Long]
     * @return [LottoNumberDto]?
     * @author yoonho
     * @since 2023.06.28
     */
    @Transactional(readOnly = true)
    fun findLottoNumber(drwtNo: Long): LottoNumberDto? =
        queryFactory
            .select(
                QLottoNumberDto(
                    lottoNumber.drwtNo,
                    lottoNumber.drwtDate,
                    lottoNumber.drwtNo1,
                    lottoNumber.drwtNo2,
                    lottoNumber.drwtNo3,
                    lottoNumber.drwtNo4,
                    lottoNumber.drwtNo5,
                    lottoNumber.drwtNo6,
                    lottoNumber.bnusNo,
                    lottoNumber.updatedAt,
                    lottoNumber.createdAt
                )
            )
            .from(lottoNumber)
            .where(
                lottoNumber.drwtNo.eq(drwtNo)
            )
            .fetchFirst()

    /**
     * 최신 로또번호 조회
     *
     * @return [LottoNumberDto]?
     * @author yoonho
     * @since 2023.07.12
     */
    @Transactional(readOnly = true)
    fun findLottoNumberLatest(): LottoNumberDto? =
        queryFactory
            .select(
                QLottoNumberDto(
                    lottoNumber.drwtNo,
                    lottoNumber.drwtDate,
                    lottoNumber.drwtNo1,
                    lottoNumber.drwtNo2,
                    lottoNumber.drwtNo3,
                    lottoNumber.drwtNo4,
                    lottoNumber.drwtNo5,
                    lottoNumber.drwtNo6,
                    lottoNumber.bnusNo,
                    lottoNumber.updatedAt,
                    lottoNumber.createdAt
                )
            )
            .from(lottoNumber)
            .orderBy(lottoNumber.drwtNo.desc())
            .fetchFirst()

    /**
     * 마지막 로또회차정보 조회
     *
     * @return [Long]
     * @author yoonho
     * @since 2023.06.28
     */
    @Transactional(readOnly = true)
    fun findLastDrwtNo(): Long? =
        queryFactory
            .select(lottoNumber.drwtNo.max().coalesce(0L))
            .from(lottoNumber)
            .fetchFirst()

    /**
     * 로또번호 저장
     *
     * @param input [LottoNumberDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.06.28
     */
//    @Transactional
    fun insertLottoNumber(input: LottoNumberDto): Long =
        queryFactory
            .insert(lottoNumber)
            .columns(
                lottoNumber.drwtNo,
                lottoNumber.drwtDate,
                lottoNumber.drwtNo1,
                lottoNumber.drwtNo2,
                lottoNumber.drwtNo3,
                lottoNumber.drwtNo4,
                lottoNumber.drwtNo5,
                lottoNumber.drwtNo6,
                lottoNumber.bnusNo,
                lottoNumber.updatedAt,
                lottoNumber.createdAt
            )
            .values(
                input.drwtNo,
                input.drwtDate,
                input.drwtNo1,
                input.drwtNo2,
                input.drwtNo3,
                input.drwtNo4,
                input.drwtNo5,
                input.drwtNo6,
                input.bnusNo,
                null,
                LocalDateTime.now()
            )
            .execute()

}