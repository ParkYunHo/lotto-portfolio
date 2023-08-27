package com.john.lotto.number.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.07.119
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LottoTotalInfo(
    @Schema(description = "당첨회차", example = "0")
    val drwtNo: Long? = -1L,

    @Schema(description = "추첨일자", example = "yyyy-MM-dd")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val drwtDate: LocalDate? = null,
    @Schema(description = "당첨번호1", example = "0")
    val drwtNo1: Long? = -1L,
    @Schema(description = "당첨번호2", example = "0")
    val drwtNo2: Long? = -1L,
    @Schema(description = "당첨번호3", example = "0")
    val drwtNo3: Long? = -1L,
    @Schema(description = "당첨번호4", example = "0")
    val drwtNo4: Long? = -1L,
    @Schema(description = "당첨번호5", example = "0")
    val drwtNo5: Long? = -1L,
    @Schema(description = "당첨번호6", example = "0")
    val drwtNo6: Long? = -1L,
    @Schema(description = "보너스번호", example = "0")
    val bnusNo: Long? = -1L,

    @Schema(description = "총 판매금액", example = "0")
    val totSellAmount: Long? = -1L,   // 총 판매금액
    @Schema(description = "1등 1게임당 당첨금액", example = "0")
    val firstWinAmount: Long? = -1L,  // 1등 1게임당 당첨금액
    @Schema(description = "1등 당첨게임수", example = "0")
    val firstWinCount: Long? = -1L,    // 1등 당첨게임수
    @Schema(description = "1등 전체 당첨금액", example = "0")
    val firstTotAmount: Long? = -1L,    // 1등 전체 당첨금액
)
