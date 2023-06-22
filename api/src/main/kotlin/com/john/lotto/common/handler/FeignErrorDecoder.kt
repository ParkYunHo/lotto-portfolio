package com.john.lotto.common.handler

import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import java.io.BufferedReader
import java.io.InputStream
import java.lang.Exception

/**
 * @author yoonho
 * @since 2023.06.22
 */
class FeignErrorDecoder: ErrorDecoder {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun decode(methodKey: String?, response: Response): Exception {
        val status = HttpStatus.resolve(response.status()) ?: HttpStatus.BAD_REQUEST
        val body = this.getInputStreamText(input = response.body().asInputStream())

        if(status.is4xxClientError) {
            log.warn(" >>> [decode] 4xx Client Error - methodKey: $methodKey, status: ${response.status()}, body: $body")
            throw BadRequestException()
        }else if(status.is5xxServerError) {
            log.warn(" >>> [decode] 5xx Server Error - methodKey: $methodKey, status: ${response.status()}, body: $body")
            throw InternalServerException()
        }

        return ErrorDecoder.Default().decode(methodKey, response)
    }

    private fun getInputStreamText(input: InputStream): String {
        val reader = BufferedReader(input.reader())
        lateinit var content: String
        reader.use {
            content = it.readText()
        }
        return content
    }
}