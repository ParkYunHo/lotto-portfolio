package com.john.lotto.auth.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author yoonho
 * @since 2023.07.07
 */
data class TokenInfo(
    @field:JsonProperty("token_type")
    val tokenType: String,
    @field:JsonProperty("access_token")
    val accessToken: String,
    @field:JsonProperty("id_token")
    val idToken: String? = "",
    @field:JsonProperty("expires_in")
    val expiresIn: Long,
    @field:JsonProperty("refresh_token")
    val refreshToken: String,
    @field:JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Long,
    @field:JsonProperty("scope")
    val scope: String? = ""
)
