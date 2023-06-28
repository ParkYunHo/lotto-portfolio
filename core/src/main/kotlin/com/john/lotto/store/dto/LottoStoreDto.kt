package com.john.lotto.store.dto

import com.querydsl.core.annotations.QueryProjection

/**
 * @author yoonho
 * @since 2023.06.28
 */
data class LottoStoreDto @QueryProjection constructor(
    val latitude: Float? = -1F,
    val longitude: Float? = -1F,

    val bplclocplc1: String? = "",
    val bplclocplc2: String? = "",
    val bplclocplc3: String? = "",
    val bplclocplc4: String? = "",

    val bplcdorodtladres: String? = "",
    val bplclocplcdtladres: String? = "",

    val rtlrstrtelno: String? = "",

    val firmnm: String? = "",
)
