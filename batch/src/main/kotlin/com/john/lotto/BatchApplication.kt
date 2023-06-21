package com.john.lotto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.john.lotto"], exclude = [DataSourceAutoConfiguration::class])
class BatchApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-batch, application-core")
    runApplication<BatchApplication>(*args)
}
