package com.john.lotto.auth.application.dto

/**
 * @author yoonho
 * @since 2023.07.09
 */
data class JwkInfo(
    val keys: List<KeyInfo>
) {
    data class KeyInfo(
        val kid: String,
        val kty: String,
        val alg: String,
        val use: String,
        val n: String,
        val e: String,
    )
}
