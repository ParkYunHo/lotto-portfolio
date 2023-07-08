package com.john.lotto.auth.application

import com.john.lotto.auth.application.dto.TokenInfo
import com.john.lotto.auth.application.port.`in`.AuthorizeUseCase
import com.john.lotto.auth.application.port.out.AuthPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Service
class AuthService(
    private val authPort: AuthPort
): AuthorizeUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun authorize(): Mono<Void> =
        authPort.authorize()

    override fun token(state: String, code: String): Mono<TokenInfo> =
        authPort.token(state, code)
}