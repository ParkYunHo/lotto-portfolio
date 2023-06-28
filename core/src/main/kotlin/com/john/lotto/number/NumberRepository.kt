package com.john.lotto.number

import com.john.lotto.entity.QLottoNumber
import com.john.lotto.number.dto.LottoNumberDto
import com.john.lotto.number.dto.QLottoNumberDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Repository
class NumberRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

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
                    lottoNumber.drwtNo1,
                    lottoNumber.drwtNo2,
                    lottoNumber.drwtNo3,
                    lottoNumber.drwtNo4,
                    lottoNumber.drwtNo5,
                    lottoNumber.drwtNo6,
                    lottoNumber.bnusNo
                )
            )
            .from(lottoNumber)
            .where(
                lottoNumber.drwtNo.eq(drwtNo)
            )
            .fetchFirst()

    /**
     * 로또번호 저장
     *
     * @param input [LottoNumberDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.06.28
     */
    @Transactional
    fun insertLottoNumber(input: LottoNumberDto): Long =
        queryFactory
            .insert(lottoNumber)
            .columns(
                lottoNumber.drwtNo,
                lottoNumber.drwtNo1,
                lottoNumber.drwtNo2,
                lottoNumber.drwtNo3,
                lottoNumber.drwtNo4,
                lottoNumber.drwtNo5,
                lottoNumber.drwtNo6,
                lottoNumber.bnusNo
            )
            .values(
                input.drwtNo,
                input.drwtNo1,
                input.drwtNo2,
                input.drwtNo3,
                input.drwtNo4,
                input.drwtNo5,
                input.drwtNo6,
                input.bnusNo
            )
            .execute()

}