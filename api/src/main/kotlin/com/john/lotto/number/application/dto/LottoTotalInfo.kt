package com.john.lotto.number.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.07.119
 */
data class LottoTotalInfo(
    val drwtNo: Long? = -1L,

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val drwtDate: LocalDate? = null,
    val drwtNo1: Long? = -1L,
    val drwtNo2: Long? = -1L,
    val drwtNo3: Long? = -1L,
    val drwtNo4: Long? = -1L,
    val drwtNo5: Long? = -1L,
    val drwtNo6: Long? = -1L,
    val bnusNo: Long? = -1L,

    val totSellamnt: Long? = -1L,   // 총 판매금액
    val firstWinamnt: Long? = -1L,  // 1등 1게임당 당첨금액
    val firstPrzwnerCo: Long? = -1L,    // 1등 당첨게임수
    val firstAccumamnt: Long? = -1L,    // 1등 전체 당첨금액
)
