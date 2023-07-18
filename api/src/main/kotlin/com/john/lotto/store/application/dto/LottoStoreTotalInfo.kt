package com.john.lotto.store.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

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
    var drwtNos: List<Long> = mutableListOf()
)
