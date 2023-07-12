package com.john.lotto.auth.application.port.`in`

import com.john.lotto.auth.application.dto.ResultTokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.07
 */
interface AuthorizeUseCase {
    /**
     * 인가코드 요청
     *
     * @return [Mono]<[String]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun authorize(): Mono<String>

    /**
     * idToken 발급
     *
     * @param state [String]
     * @param code [String]
     * @return [Mono]<[ResultTokenInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun token(state: String, code: String): Mono<ResultTokenInfo>

    /**
     * idToken 재발급
     *
     * @param refreshToken [String]
     * @return [Mono]<[ResultTokenInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun refresh(refreshToken: String): Mono<ResultTokenInfo>
}