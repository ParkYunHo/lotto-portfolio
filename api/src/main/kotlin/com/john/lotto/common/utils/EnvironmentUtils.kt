package com.john.lotto.common.utils

import org.springframework.core.env.Environment

/**
 * @author yoonho
 * @since 2023.07.07
 */
object EnvironmentUtils {
    private lateinit var env: Environment

    fun setEnvironment(env: Environment){
        this.env = env
    }

    fun getProperty(key: String): String? =
        this.env.getProperty(key)

    fun getProperty(key: String, defaultValue: String): String =
        this.env.getProperty(key, defaultValue)
}