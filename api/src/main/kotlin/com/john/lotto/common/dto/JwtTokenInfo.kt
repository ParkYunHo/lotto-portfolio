package com.john.lotto.common.dto

import java.io.Serializable

class JwtTokenInfo {
    data class Header(
        val kid: String,
        val typ: String,
        val alg: String
    ): Serializable

    data class Payload(
        val aud: String,
        val sub: String,
        val auth_time: Int,
        val iss: String,
        val nickname: String? = "",
        val email: String? = "",
        val exp: Int,
        val iat: Int,
    ): Serializable
}
