package com.john.lotto.drwtstore.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.03
 */
data class DrwtStoreDto @QueryProjection constructor(
    val drwtNo: Long? = -1L,
    val drwtOrder: Long? = -1L,
    val drwtRank: Int? = -1,
    val rtlrid: String? = "",
    val firmnm: String? = "",
    val bplcdorodtladres: String? = "",
    val drwtType: String? = "",

    val updatedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = LocalDateTime.now()
)