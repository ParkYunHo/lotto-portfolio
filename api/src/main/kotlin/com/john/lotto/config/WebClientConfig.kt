package com.john.lotto.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.client.reactive.ReactorResourceFactory
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Configuration
class WebrClientConfig {

    @Value("\${auth.conn.connect-timeout}")
    private lateinit var connectionTimeout: String
    @Value("\${auth.conn.read-timeout}")
    private lateinit var readTimeout: String
    @Value("\${auth.conn.user-agent}")
    private lateinit var userAgent: String

    @Bean
    fun defaultWebClient(reactorResourceFactory: ReactorResourceFactory): WebClient {
        val mapper: (HttpClient) -> HttpClient = {
            it.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout.toInt())
                .doOnConnected {
                    it.addHandlerLast(ReadTimeoutHandler(readTimeout.toLong(), TimeUnit.MILLISECONDS))
                }
//                .followRedirect(true)
        }

        val customExchangeStrategies = ExchangeStrategies.builder()
            .codecs {
                it.defaultCodecs().maxInMemorySize(1024 * 1024 * 10)
                it.customCodecs().registerWithDefaultConfig(Jackson2JsonDecoder(ObjectMapper(), MediaType.TEXT_HTML))
                it.customCodecs().registerWithDefaultConfig(Jackson2JsonEncoder(ObjectMapper(), MediaType.TEXT_HTML))
            }
            .build()

        return WebClient.builder()
            .defaultHeaders {
                it.set("User-Agent", userAgent)
            }
            .clientConnector(ReactorClientHttpConnector(reactorResourceFactory, mapper))
            .exchangeStrategies(customExchangeStrategies)
            .build()
    }
}