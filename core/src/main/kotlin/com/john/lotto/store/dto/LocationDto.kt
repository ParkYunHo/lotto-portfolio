package com.john.lotto.store.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection

/**
 * @author yoonho
 * @since 2023.07.18
 */
data class LocationDto @QueryProjection constructor(
    @JsonProperty("BPLCLOCPLC1")
    val bplclocplc1: String? = "",
    @JsonProperty("BPLCLOCPLC2")
    val bplclocplc2: String? = "",
)
