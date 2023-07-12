package com.john.lotto.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.lotto.common.utils.EnvironmentUtils
import com.john.lotto.common.utils.ObjectMapperUtils
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Configuration
class AppConfig(
    private val env: Environment,
    private val objectMapper: ObjectMapper
) {

    @PostConstruct
    fun init() {
        EnvironmentUtils.setEnvironment(env)
        ObjectMapperUtils.init(objectMapper)
    }
}