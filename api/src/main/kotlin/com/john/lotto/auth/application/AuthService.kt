package com.john.lotto.auth.application

import com.john.lotto.auth.application.dto.ResultTokenInfo
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

    override fun authorize(): Mono<String> =
        authPort.authorize()

    override fun token(state: String, code: String): Mono<ResultTokenInfo> =
        authPort.token(state, code)
            .flatMap {
                Mono.just(ResultTokenInfo(token = it.idToken!!))
            }
}