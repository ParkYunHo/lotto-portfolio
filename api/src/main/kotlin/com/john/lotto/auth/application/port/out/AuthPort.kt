package com.john.lotto.auth.application.port.out

import com.john.lotto.auth.application.dto.TokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.07
 */
interface AuthPort {
    fun authorize(): Mono<Void>

    fun token(state: String, code: String): Mono<TokenInfo>
}