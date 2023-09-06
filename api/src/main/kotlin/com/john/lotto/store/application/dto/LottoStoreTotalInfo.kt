package com.john.lotto.store.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.07.18
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LottoStoreTotalInfo(
    @Schema(description = "판매점 고유ID", example = "1111")
    val storeId: String? = "",

    @Schema(description = "위도", example = "0")
    val latitude: Float? = -1F,
    @Schema(description = "경도", example = "0")
    val longitude: Float? = -1F,

    @Schema(description = "행정구역(시/도)", example = "0")
    val address1: String? = "",
    @Schema(description = "행정구역(구)", example = "0")
    val address2: String? = "",
    @Schema(description = "행정구역(동)", example = "0")
    val address3: String? = "",
    @Schema(description = "행정구역(상세주소)", example = "0")
    val address4: String? = "",

    @Schema(description = "도로명주소", example = "0")
    var newAddress: String? = "",
    @Schema(description = "지번주소", example = "0")
    var oldAddress: String? = "",
    @Schema(description = "판매점 전화번호", example = "0")
    val phoneNo: String? = "",
    @Schema(description = "상호명", example = "0")
    var storeName: String? = "",

    @Schema(description = "명당여부", example = "true")
    var isGoodPlace: Boolean = false,
    @Schema(description = "스크랩된 판매점 여부", example = "true")
    var isScrap: Boolean = false,

    // 1등 당첨회차
    @Schema(description = "당첨정보 리스트", example = "0", type = "array", implementation = LottoStoreTotalInfo.DrwtInfo::class)
    var drwtInfos: List<DrwtInfo> = mutableListOf()
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class DrwtInfo(
        @Schema(description = "당첨회차", example = "0")
        val drwtNo: Long,
        @Schema(description = "추첨일자", example = "yyyy-MM-dd")
        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val drwtDate: LocalDate,
        @Schema(description = "1등 1게임당 당첨금액", example = "0")
        val firstWinAmount: Long,
        @Schema(description = "1등 당첨게임수", example = "0")
        val firstWinCount: Long
    )
}
