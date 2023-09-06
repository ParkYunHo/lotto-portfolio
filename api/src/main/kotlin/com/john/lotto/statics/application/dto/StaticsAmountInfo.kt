package com.john.lotto.statics.application.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.09.06
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StaticsAmountInfo(
    @Schema(description = "당첨회차", example = "0")
    val drwtNo: Long? = -1L,
    @Schema(description = "1등 1게임당 당첨금액", example = "0")
    val firstWinAmount: Long? = -1L,  // 1등 1게임당 당첨금액
    @Schema(description = "1등 1게임당 당첨금액(세후)", example = "0")
    val firstWinAmountTax: Long? = -1L,  // 1등 1게임당 당첨금액(세후)
): Serializable
