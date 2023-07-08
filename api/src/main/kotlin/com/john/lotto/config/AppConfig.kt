package com.john.lotto.config

import com.john.lotto.common.utils.EnvironmentUtils
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment

/**
 * @author yoonho
 * @since 2023.07.07
 */
@Configuration
class AppConfig(
    private val env: Environment
) {

    @PostConstruct
    fun init() {
        EnvironmentUtils.setEnvironment(env)
    }
}