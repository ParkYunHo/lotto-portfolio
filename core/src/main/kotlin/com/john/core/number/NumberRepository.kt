package com.john.core.number

import com.john.core.entity.QLottoNumber
import com.john.core.number.dto.LottoNumberDto
import com.john.core.number.dto.QLottoNumberDto
import com.querydsl.jpa.impl.JPAQueryFactory
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

    private val lottoNumber = QLottoNumber.lottoNumber!!

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
}