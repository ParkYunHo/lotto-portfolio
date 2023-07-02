package com.john.lotto.store.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.john.lotto.entity.LottoStore
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.28
 */
data class LottoStoreDto @QueryProjection constructor(
    @JsonProperty("RTLRID")
    val rtlrid: String? = "",

    @JsonProperty("LATITUDE")
    val latitude: Float? = -1F,
    @JsonProperty("LONGITUDE")
    val longitude: Float? = -1F,

    @JsonProperty("BPLCLOCPLC1")
    val bplclocplc1: String? = "",
    @JsonProperty("BPLCLOCPLC2")
    val bplclocplc2: String? = "",
    @JsonProperty("BPLCLOCPLC3")
    val bplclocplc3: String? = "",
    @JsonProperty("BPLCLOCPLC4")
    val bplclocplc4: String? = "",

    @JsonProperty("BPLCDORODTLADRES")
    var bplcdorodtladres: String? = "",
    @JsonProperty("BPLCLOCPLCDTLADRES")
    var bplclocplcdtladres: String? = "",
    @JsonProperty("RTLRSTRTELNO")
    val rtlrstrtelno: String? = "",
    @JsonProperty("FIRMNM")
    var firmnm: String? = "",

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonProperty("UPDATED_AT")
    val updatedAt: LocalDateTime? = null,
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonProperty("CREATED_AT")
    val createdAt: LocalDateTime? = LocalDateTime.now()
) {
    fun toEntity(): LottoStore =
        LottoStore(
            rtlrid = this.rtlrid ?: "",
            latitude = this.latitude ?: -1F,
            longitude = this.longitude ?: -1F,
            bplclocplc1 = this.bplclocplc1 ?: "",
            bplclocplc2 = this.bplclocplc2 ?: "",
            bplclocplc3 = this.bplclocplc3 ?: "",
            bplclocplc4 = this.bplclocplc4 ?: "",
            bplcdorodtladres = this.bplcdorodtladres ?: "",
            bplclocplcdtladres = this.bplclocplcdtladres ?: "",
            rtlrstrtelno = this.rtlrstrtelno ?: "",
            firmnm = this.firmnm ?: "",
            updatedAt = this.updatedAt ?: LocalDateTime.now(),
            createdAt = this.createdAt ?: LocalDateTime.now(),
        )
}
