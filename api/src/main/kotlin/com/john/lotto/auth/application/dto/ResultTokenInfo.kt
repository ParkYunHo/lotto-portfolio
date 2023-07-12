package com.john.lotto.auth.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author yoonho
 * @since 2023.07.09
 */
data class ResultTokenInfo(
    @field:JsonProperty("id_token")
    val idToken: String,
    @field:JsonProperty("refresh_token")
    val refreshToken: String? = "",
    @field:JsonProperty("nickname")
    val nickname: String? = ""
)
