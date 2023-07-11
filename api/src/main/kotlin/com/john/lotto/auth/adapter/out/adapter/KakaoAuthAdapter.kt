package com.john.lotto.auth.adapter.out.adapter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.lotto.auth.application.dto.JwkInfo
import com.john.lotto.auth.application.dto.TokenInfo
import com.john.lotto.auth.application.port.out.AuthPort
import com.john.lotto.common.dto.JwtTokenInfo
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.common.exception.UnAuthorizedException
import com.john.lotto.common.utils.EnvironmentUtils
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Component
class KakaoAuthAdapter(
    private val defaultWebClient: WebClient,
    private val objectMapper: ObjectMapper
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

    override fun keys(): Mono<JwkInfo> {
        try {
            val kauthUrl = EnvironmentUtils.getProperty("auth.api.kauth", "https://kauth.kakao.com")

            val uriComponents = UriComponentsBuilder
                .fromHttpUrl( "$kauthUrl/.well-known/jwks.json")
                .build(false)

            return defaultWebClient
                .get()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono {
                    if(it.statusCode().is2xxSuccessful) {
                        return@exchangeToMono it.bodyToMono(JwkInfo::class.java)
                    }else{
                        return@exchangeToMono throw InternalServerException("일시적으로 카카오서버를 이용할 수 없습니다.")
                    }
                }
        }catch (e: Exception) {
            throw e
        }
    }

    override fun validate(idToken: String): Mono<String> =
        this.keys()
            .flatMap {
                // https://developers.kakao.com/docs/latest/ko/kakaologin/common#oidc
                val payload = this.decodePayload(idToken.split(".")[1])

                // 발급기관 체크
                val kauthUrl = EnvironmentUtils.getProperty("auth.url.kauth", "")
                if(payload.iss != kauthUrl) {
                    return@flatMap Mono.error(UnAuthorizedException("유효한 발급인증기관이 아닙니다 - iss: ${payload.iss}"))
                }

                // 서비스앱키 체크
                val clientId = EnvironmentUtils.getProperty("auth.key.client-id", "")
                if(payload.aud != clientId) {
                    return@flatMap Mono.error(UnAuthorizedException("유효한 서비스앱키가 아닙니다 - aud: ${payload.aud}"))
                }

                // 만료시간 체크
                val instant = Instant.ofEpochSecond(payload.exp.toLong())
                val exp = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId())
                if(LocalDateTime.now().isAfter(exp)) {
                    return@flatMap Mono.error(UnAuthorizedException("유효한 ID토큰이 아닙니다 - exp: ${payload.exp}, parsedExp: $exp"))
                }

                val header = this.decodeHeader(idToken.split(".")[0])
                val keyInfo = it.keys.first { key -> key.kid == header.kid }

                // TODO: 서명검증하는법 찾아보기!!!!
                // 서명검증
                try {
                    val jwtParser = Jwts.parserBuilder()
                        .setSigningKey(
                            KeyFactory.getInstance("RSA")
                                .generatePublic(
                                    RSAPublicKeySpec(
                                        BigInteger(1, Base64.getUrlDecoder().decode(keyInfo.n)),
                                        BigInteger(1, Base64.getUrlDecoder().decode(keyInfo.e))
                                    )
                                )
                        )
                        .build()
                }catch (e: Exception) {
                    return@flatMap Mono.error(UnAuthorizedException("유효한 서명이 아닙니다"))
                }

                // 유효성체크를 통과했을 경우, accountId 응답
                Mono.just(payload.sub)
            }

    private fun decodePayload(payload: String): JwtTokenInfo.Payload =
        objectMapper.readValue(
            String(Base64.getUrlDecoder().decode(payload)),
            object: TypeReference<JwtTokenInfo.Payload>() {}
        )

    private fun decodeHeader(header: String): JwtTokenInfo.Header =
        objectMapper.readValue(
            String(Base64.getUrlDecoder().decode(header)),
            object: TypeReference<JwtTokenInfo.Header>() {}
        )
}