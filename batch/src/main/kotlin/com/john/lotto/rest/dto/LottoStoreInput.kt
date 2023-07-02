package com.john.lotto.rest.dto

import lombok.ToString
import java.io.Serializable
import kotlin.reflect.full.memberProperties

/**
 * @author yoonho
 * @since 2023.06.28
 */
@ToString
data class LottoStoreInput(
    val searchType: String? = "",
    val nowPage: String? = "",
    val rtlrSttus: String? = "",
    val sltSIDO2: String? = "",
    val sltGUGUN2: String? = ""
): Serializable {
    fun asMap(): Map<String, Any?> =
        LottoStoreInput::class.memberProperties.associate { it.name to it.get(this) }
}
