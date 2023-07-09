package com.john.lotto.auth.adapter.out.adapter

import com.john.lotto.auth.application.dto.TokenInfo
import com.john.lotto.auth.application.port.out.AuthPort
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.common.utils.EnvironmentUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KakaoAuthAdapter(
    private val defaultWebClient: WebClient
): AuthPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun authorize(): Mono<String> {
        try {
            val kauthUrl = EnvironmentUtils.getProperty("auth.api.kauth", "https://kauth.kakao.com")
            val clientId = EnvironmentUtils.getProperty("auth.key.client-id", "")

            val queryParams = LinkedMultiValueMap<String, String>()
            queryParams.add("client_id", clientId)
            queryParams.add("redirect_uri", "http://localhost:8080/auth/token")
            queryParams.add("response_type", "code")
            queryParams.add("scope", "openid")
            queryParams.add("state", "kakao")

            val uriComponent = UriComponentsBuilder
                .fromHttpUrl("$kauthUrl/oauth/authorize")
                .queryParams(queryParams)
                .build(false)

            log.info(" >>> [authorize] request - url: ${uriComponent.toUriString()}")
            return defaultWebClient
                .get()
                .uri(uriComponent.toUri())
                .exchangeToMono {
                    if(it.statusCode().is3xxRedirection) {
                        val location = it.headers().header(HttpHeaders.LOCATION).firstOrNull()
                        if(!location.isNullOrEmpty()) {
                            return@exchangeToMono Mono.just(location)
                        }
                    }
                    return@exchangeToMono Mono.empty()
                }
        }catch (e: Exception) {
            throw e
        }
    }

    override fun token(state: String, code: String): Mono<TokenInfo> {
        try{
            val kauthUrl = EnvironmentUtils.getProperty("auth.api.kauth", "https://kauth.kakao.com")
            val clientId = EnvironmentUtils.getProperty("auth.key.client-id", "")

            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("grant_type", "authorization_code")
            params.add("client_id", clientId)
            params.add("code", code)

            val uriComponents = UriComponentsBuilder
                .fromHttpUrl( "$kauthUrl/oauth/token")
                .queryParams(params)
                .build(false)

            log.info(" >>> [token] request - url: ${uriComponents.toUriString()}")
            return defaultWebClient
                .post()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono {
                    if(it.statusCode().is2xxSuccessful) {
                        return@exchangeToMono it.bodyToMono(TokenInfo::class.java)
                    }else if(it.statusCode().is4xxClientError) {
                        return@exchangeToMono throw BadRequestException("유효한 인가코드가 아닙니다.")
                    }else{
                        return@exchangeToMono throw InternalServerException("일시적으로 카카오서버를 이용할 수 없습니다.")
                    }
                }
                .log()
        }catch (e: Exception) {
            throw e
        }
    }
}