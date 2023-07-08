package com.john.lotto.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.client.reactive.ReactorResourceFactory
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Order(value = Ordered.LOWEST_PRECEDENCE)
@Configuration
class WebrClientConfig {

    @Value("\${auth.conn.connect-timeout}")
    private lateinit var connectionTimeout: String
    @Value("\${auth.conn.read-timeout}")
    private lateinit var readTimeout: String
    @Value("\${auth.conn.user-agent}")
    private lateinit var userAgent: String

//    @Bean
//    fun defaultWebClient(reactorResourceFactory: ReactorResourceFactory): WebClient {
//        val mapper: (HttpClient) -> HttpClient = {
//            it.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout.toInt())
//                .doOnConnected {
//                    it.addHandlerLast(ReadTimeoutHandler(readTimeout.toLong(), TimeUnit.MILLISECONDS))
//                }
//        }
//
//        val customExchangeStrategies = ExchangeStrategies.builder()
//            .codecs { it.defaultCodecs().maxInMemorySize(1024 * 1024 * 10) }
//            .build()
//
//        return WebClient.builder()
//            .defaultHeaders {
//                it.set("User-Agent", userAgent)
//            }
//            .clientConnector(ReactorClientHttpConnector(reactorResourceFactory, mapper))
//            .exchangeStrategies(customExchangeStrategies)
//            .build()
//    }

    @Bean
    fun defaultWebClient(httpClient: HttpClient): WebClient {
        val mapper: (HttpClient) -> HttpClient = {
            it.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout.toInt())
                .doOnConnected {
                    it.addHandlerLast(ReadTimeoutHandler(readTimeout.toLong(), TimeUnit.MILLISECONDS))
                }
        }

        val customExchangeStrategies = ExchangeStrategies.builder()
            .codecs { it.defaultCodecs().maxInMemorySize(1024 * 1024 * 10) }
            .build()

        return WebClient.builder()
            .defaultHeaders {
                it.set("User-Agent", userAgent)
            }
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(customExchangeStrategies)
            .build()
    }

    @Bean
    fun httpClient(): HttpClient =
        HttpClient.create()
            .compress(true)
            .followRedirect(true)
}