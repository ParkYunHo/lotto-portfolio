package com.john.lotto.number.dto

import com.querydsl.core.annotations.QueryProjection

/**
 * @author yoonho
 * @since 2023.06.21
 */
data class LottoNumberDto @QueryProjection constructor(
    val drwtNo: Long? = -1L,

    val drwtNo1: Long? = -1L,
    val drwtNo2: Long? = -1L,
    val drwtNo3: Long? = -1L,
    val drwtNo4: Long? = -1L,
    val drwtNo5: Long? = -1L,
    val drwtNo6: Long? = -1L,
    val bnusNo: Long? = -1L,
)
