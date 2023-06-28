package com.john.lotto.amount.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.06.21
 */
data class LottoWinAmountDto @QueryProjection constructor(
    val drwtNo: Long? = -1L,

    val drwtDate: LocalDate?,
    val totSellamnt: Long? = -1L,
    val firstWinamnt: Long? = -1L,
    val firstPrzwnerCo: Long? = -1L,
    val firstAccumamnt: Long? = -1L,
)
