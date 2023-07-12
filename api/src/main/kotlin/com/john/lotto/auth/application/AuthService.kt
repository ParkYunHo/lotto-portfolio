package com.john.lotto.auth.application

import com.john.lotto.auth.application.dto.ResultTokenInfo
import com.john.lotto.auth.application.port.`in`.AuthorizeUseCase
import com.john.lotto.auth.application.port.out.AuthPort
import com.john.lotto.common.dto.JwtTokenInfo
import com.john.lotto.common.utils.ObjectMapperUtils
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

    /**
     * 인가코드 요청
     *
     * @return [Mono]<[String]>
     * @author yoonho
     * @since 2023.07.12
     */
    override fun authorize(): Mono<String> =
        authPort.authorize()

    /**
     * idToken 발급
     *
     * @param state [String]
     * @param code [String]
     * @return [Mono]<[ResultTokenInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    override fun token(state: String, code: String): Mono<ResultTokenInfo> =
        authPort.token(state, code)
            .flatMap {
                val nickname = ObjectMapperUtils.decode(
                    it.idToken.split(".")[1],
                    JwtTokenInfo.Payload::class.java
                ).nickname

                Mono.just(
                    ResultTokenInfo(
                        idToken = it.idToken,
                        refreshToken = it.refreshToken,
                        nickname = nickname
                    )
                )
            }

    /**
     * idToken 재발급
     *
     * @param refreshToken [String]
     * @return [Mono]<[ResultTokenInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    override fun refresh(refreshToken: String): Mono<ResultTokenInfo> =
        authPort.refresh(refreshToken = refreshToken)
            .flatMap {
                val nickname = ObjectMapperUtils.decode(
                    it.idToken.split(".")[1],
                    JwtTokenInfo.Payload::class.java
                ).nickname

                Mono.just(
                    ResultTokenInfo(
                        idToken = it.idToken,
                        refreshToken = it.refreshToken,
                        nickname = nickname
                    )
                )
            }
}