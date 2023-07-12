package com.john.lotto.auth.application.port.out

import com.john.lotto.auth.application.dto.JwkInfo
import com.john.lotto.auth.application.dto.TokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.07
 */
interface AuthPort {
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
     * @return [Mono]<[TokenInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun token(state: String, code: String): Mono<TokenInfo>

    /**
     * publicKey 조회
     *
     * @return [Mono]<[JwkInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun keys(): Mono<JwkInfo>

    /**
     * idToken 유효성체크
     *
     * @param idToken [String]
     * @param jwtInfo [JwkInfo]
     * @return [Mono]<[String]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun validate(idToken: String, jwtInfo: JwkInfo): Mono<String>

    /**
     * idToken 재발급
     *
     * @param refreshToken [String]
     * @return [Mono]<[TokenInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun refresh(refreshToken: String): Mono<TokenInfo>
}