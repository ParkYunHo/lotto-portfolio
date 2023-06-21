package com.john.lotto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-api, application-core")
    runApplication<ApiApplication>(*args)
}
