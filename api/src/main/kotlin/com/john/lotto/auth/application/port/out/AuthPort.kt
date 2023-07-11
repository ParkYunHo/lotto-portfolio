package com.john.lotto.auth.application.port.out

import com.john.lotto.auth.application.dto.JwkInfo
import com.john.lotto.auth.application.dto.TokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.07
 */
interface AuthPort {
    fun authorize(): Mono<String>

    fun token(state: String, code: String): Mono<TokenInfo>

    fun keys(): Mono<JwkInfo>

    fun validate(idToken: String): Mono<String>
}