package com.john.lotto.common.filter

import com.john.lotto.auth.application.port.out.AuthPort
import com.john.lotto.common.exception.UnAuthorizedException
import com.john.lotto.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

private const val DEV_PREFIX = "DEV_LOTTO_FOLIO"
private const val TOKEN_PREFIX = "Bearer "
private const val USER_ID_ATTRIBUTE = "userId"

/**
 * @author yoonho
 * @since 2023.07.11
 */
@Component
class AuthorizationFilter(
    private val authPort: AuthPort,
    private val memberRepository: MemberRepository
): WebFilter {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${auth.white-list}")
    private lateinit var whiteList: List<String>

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        // WhiteList에 포함된 경로인 경우, 인증로직을 수행하지 않음
        val path = exchange.request.path.pathWithinApplication().value()
        whiteList.forEach {
            if(Regex("$it*") in path) {
                return chain.filter(exchange)
            }
        }

        val authorization = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: throw UnAuthorizedException("Authorization 헤더가 존재하지 않습니다.")

        // 개발용 토큰인 경우 인증제외처리
        if(DEV_PREFIX in authorization) {
            exchange.attributes[USER_ID_ATTRIBUTE] = "TEST_USER_ID"
            return chain.filter(exchange)
        }

        if(TOKEN_PREFIX in authorization) {
            val idToken = authorization.substringAfter(TOKEN_PREFIX)
            if(idToken.isNotEmpty()) {
                // idToken 유효성검증
                return authPort.keys()
                    .flatMap { jwtInfo ->
                        authPort.validate(idToken, jwtInfo)
                            .flatMap {
                                // 사용자등록 API의 경우 회원체크로직 제외
                                if(path == "/api/member" && exchange.request.method.matches(HttpMethod.POST.name())) {
                                    return@flatMap Mono.just(it)
                                }

                                // 회원정보 등록여부 체크
                                val existsMember = memberRepository.findMember(userId = it)
                                if(existsMember == null) {
                                    return@flatMap Mono.error(UnAuthorizedException("등록된 회원이 아닙니다 - userId: $it"))
                                }
                                return@flatMap Mono.just(it)
                            }
                            .flatMap {
                                exchange.attributes[USER_ID_ATTRIBUTE] = it
                                chain.filter(exchange)
                            }
                    }
            }
        }

        throw UnAuthorizedException("필수 인증정보가 존재하지 않습니다.")
    }
}

fun ServerRequest.userId(): String = this.attribute(USER_ID_ATTRIBUTE).get().toString()