package com.john.lotto.auth.application.port.`in`

import com.john.lotto.auth.application.dto.ResultTokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.07
 */
interface AuthorizeUseCase {
    fun authorize(): Mono<String>

    fun token(state: String, code: String): Mono<ResultTokenInfo>
}