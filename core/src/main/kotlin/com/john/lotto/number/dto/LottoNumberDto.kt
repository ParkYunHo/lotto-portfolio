package com.john.lotto.number.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.21
 */
data class LottoNumberDto @QueryProjection constructor(
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

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime? = null,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime? = null
)
