package com.john.lotto.config

import com.john.lotto.common.handler.FeignErrorDecoder
import feign.RequestInterceptor
import feign.Retryer
import feign.codec.Encoder
import feign.codec.ErrorDecoder
import feign.form.spring.SpringFormEncoder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignFormatterRegistrar
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Configuration
@EnableFeignClients(basePackages = ["com.john.lotto"])
class OpenFeignConfig {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * LocalDateTime Format 설정
     *
     * @see <a href="https://techblog.woowahan.com/2630/">Feign</a>
     * @return [FeignFormatterRegistrar]
     * @author yoonho
     * @since 2023.06.21
     */
    @Bean
    fun localDateFeignFormatterRegister(): FeignFormatterRegistrar =
        FeignFormatterRegistrar { registry ->
            val registrar = DateTimeFormatterRegistrar()
            registrar.setUseIsoFormat(true)
            registrar.registerFormatters(registry)
        }

    /**
     * Request Interceptor
     *
     * @return [RequestInterceptor]
     * @author yoonho
     * @since 2023.06.21
     */
    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor { interceptor ->
            log.info(" >>> [requestInterceptor] path: ${interceptor.path()}, method: ${interceptor.method()}, body: ${interceptor.body()?.let { it.toString(StandardCharsets.UTF_8) } ?: ""}, queries: ${interceptor.queries()}, header: ${interceptor.headers()}")
        }

    /**
     * Error 핸들러 등록
     *
     * @return [ErrorDecoder]
     * @author yoonho
     * @since 2023.06.21
     */
    @Bean
    fun errorDecoder(): ErrorDecoder =
        FeignErrorDecoder()

    @Bean
    fun encoder(converters: ObjectFactory<HttpMessageConverters>): Encoder =
        SpringFormEncoder(SpringEncoder(converters))

    @Bean
    fun retryer(): Retryer.Default =
        Retryer.Default(1000L, TimeUnit.SECONDS.toMillis(1000L), 3)
}