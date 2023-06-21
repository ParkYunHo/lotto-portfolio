package com.john.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatchApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-batch, application-core")
    runApplication<BatchApplication>(*args)
}
