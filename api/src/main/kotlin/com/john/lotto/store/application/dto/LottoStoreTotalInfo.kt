package com.john.lotto.store.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.07.18
 */
data class LottoStoreTotalInfo(
    @JsonProperty("RTLRID")
    val rtlrid: String? = "",

    @JsonProperty("LATITUDE")
    val latitude: Float? = -1F,
    @JsonProperty("LONGITUDE")
    val longitude: Float? = -1F,

    @JsonProperty("BPLCLOCPLC1")
    val bplclocplc1: String? = "",
    @JsonProperty("BPLCLOCPLC2")
    val bplclocplc2: String? = "",
    @JsonProperty("BPLCLOCPLC3")
    val bplclocplc3: String? = "",
    @JsonProperty("BPLCLOCPLC4")
    val bplclocplc4: String? = "",

    @JsonProperty("BPLCDORODTLADRES")
    var bplcdorodtladres: String? = "",
    @JsonProperty("BPLCLOCPLCDTLADRES")
    var bplclocplcdtladres: String? = "",
    @JsonProperty("RTLRSTRTELNO")
    val rtlrstrtelno: String? = "",
    @JsonProperty("FIRMNM")
    var firmnm: String? = "",

    // 1등 당첨회차
    var drwtInfos: List<DrwtInfo> = mutableListOf()
) {
    data class DrwtInfo(
        val drwtNo: Long,
        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val drwtDate: LocalDate,
        val firstWinamnt: Long,
        val firstPrzwnerCo: Long
    )
}
