package com.john.lotto.auth.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author yoonho
 * @since 2023.07.09
 */
data class ResultTokenInfo(
    @field:Schema(description = "OpenID방식의 id_token", example = "header.payload.signature")
    @field:JsonProperty("id_token")
    val idToken: String,
    @field:Schema(description = "id_token 만료시간", example = "0")
    @field:JsonProperty("expires_in")
    val expiresIn: Long? = 0L,
    @field:Schema(description = "리프레시 토큰", example = "0")
    @field:JsonProperty("refresh_token")
    val refreshToken: String? = "",
    @field:Schema(description = "리프레시 토큰 만료시간", example = "0")
    @field:JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Long? = 0L,
    @field:Schema(description = "사용자 닉네임", example = "user")
    @field:JsonProperty("nickname")
    val nickname: String? = "",
    @field:Schema(description = "사용자 이메일", example = "lotto@email.com")
    @field:JsonProperty("email")
    val email: String? = ""
)
