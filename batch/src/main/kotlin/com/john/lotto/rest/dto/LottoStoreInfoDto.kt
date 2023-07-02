package com.john.lotto.rest.dto

import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.06.28
 */
data class LottoStoreInfoDto(
    val arr: MutableList<Any> = mutableListOf(),

    val pageIsNext: Boolean,
    val pageIsPrev: Boolean,
    val pageStart: Int,
    val pageEnd: Int,
    val totalPage: Int,
    val sltSido2: String,
    val nowPage: Int,
): Serializable
