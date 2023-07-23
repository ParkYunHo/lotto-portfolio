package com.john.lotto.scrap.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.23
 */
data class StoreScrapDto @QueryProjection constructor(
    val userId: String,
    val rtlrid: String,

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime? = null,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
)
