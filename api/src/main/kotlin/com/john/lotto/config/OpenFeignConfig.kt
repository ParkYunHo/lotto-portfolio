package com.john.lotto.config

import com.john.lotto.common.handler.FeignErrorDecoder
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignFormatterRegistrar
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.http.MediaType

/**
 * @author yoonho
 * @since 2023.06.22
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
     * @since 2023.06.22
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
     * @since 2023.06.22
     */
    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor { interceptor ->
            log.info(" >>> [requestInterceptor] path: ${interceptor.path()}, method: ${interceptor.method()}, body: ${interceptor.body()}, queries: ${interceptor.queries()}")
            interceptor.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        }

    /**
     * Error 핸들러 등록
     *
     * @return [ErrorDecoder]
     * @author yoonho
     * @since 2023.06.22
     */
    @Bean
    fun errorDecoder(): ErrorDecoder =
        FeignErrorDecoder()
}