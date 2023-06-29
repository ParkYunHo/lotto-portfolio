package com.john.lotto.rest.dto

/**
 * @author yoonho
 * @since 2023.06.28
 */
data class TotalLottoNumberDto(
    val drwNo: Long? = -1L,

    val drwNoDate: String?,
    val totSellamnt: Long? = null,
    val firstWinamnt: Long? = null,
    val firstPrzwnerCo: Long? = -1,
    val firstAccumamnt: Long? = null,

    val drwtNo1: Long? = -1,
    val drwtNo2: Long? = -1,
    val drwtNo3: Long? = -1,
    val drwtNo4: Long? = -1,
    val drwtNo5: Long? = -1,
    val drwtNo6: Long? = -1,
    val bnusNo: Long? = -1,

    val returnValue: String?,
)
