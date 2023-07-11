package com.john.lotto.common.filter

import com.john.lotto.auth.application.port.out.AuthPort
import com.john.lotto.common.exception.UnAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


private const val TOKEN_PREFIX = "Bearer "
private const val USER_ID_ATTRIBUTE = "userId"

/**
 * @author yoonho
 * @since 2023.07.11
 */
@Component
class AuthorizationFilter(
    private val authPort: AuthPort,
): WebFilter {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${auth.white-list}")
    private lateinit var whiteList: List<String>
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        // WhiteList에 포함된 경로인 경우, 인증로직을 수행하지 않음
        val path = exchange.request.path.pathWithinApplication().value()
        if(whiteList.contains(path)) {
            return chain.filter(exchange)
        }

        val authorization = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: throw UnAuthorizedException("Authorization 헤더가 존재하지 않습니다.")

        if(TOKEN_PREFIX in authorization) {
            val idToken = authorization.substringAfter(TOKEN_PREFIX)
            if(idToken.isNotEmpty()) {
                return authPort.validate(idToken)
                    .flatMap {
                        exchange.attributes[USER_ID_ATTRIBUTE] = it
                        chain.filter(exchange)
                    }
            }
        }

        throw UnAuthorizedException("필수 인증정보가 존재하지 않습니다.")
    }
}

fun ServerRequest.userId(): String = this.attribute(USER_ID_ATTRIBUTE).get().toString()