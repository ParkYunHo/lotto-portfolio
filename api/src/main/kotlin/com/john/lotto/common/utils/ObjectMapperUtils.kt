package com.john.lotto.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.lotto.common.exception.InternalServerException
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @author yoonho
 * @since 2023.07.12
 */
object ObjectMapperUtils {
    private val log = LoggerFactory.getLogger(this::class.java)

    lateinit var objectMapper: ObjectMapper

    /**
     * ObjectMapper 초기화
     *
     * @param objectMapper [ObjectMapper]
     * @author yoonho
     * @since 2023.07.12
     */
    fun init(objectMapper: ObjectMapper) {
        this.objectMapper = objectMapper
    }

    /**
     * 오브젝트 매핑
     *
     * @param sourceText [String]
     * @param classType [Class]<[T]>
     * @return T
     * @author yoonho
     * @since 2023.07.12
     */
    fun <T> decode(sourceText: String, classType: Class<T>): T =
        try {
            objectMapper.readValue(
                String(Base64.getUrlDecoder().decode(sourceText)),
                classType
            )
        }catch (e: Exception) {
            log.error(" >>> [decode] sourceText를 매핑하는데 실패하였습니다 - sourceText: $sourceText, classType: $classType")
            throw InternalServerException("sourceText를 매핑하는데 실패하였습니다")
        }


    /**
     * 오브젝트 매핑
     *
     * @param source [Map]<[String], [Long]>
     * @param classType [Class]<[T]>
     * @return T
     * @author yoonho
     * @since 2023.07.12
     */
    fun <T> decode(source: Map<String, Long>, classType: Class<T>): T =
        try {
            objectMapper.convertValue(source, classType)
        }catch (e: Exception) {
            log.error(" >>> [decode] source를 매핑하는데 실패하였습니다 - source: $source, classType: $classType")
            throw InternalServerException("source를를 매핑하는데 실패하였습니다")
        }

}