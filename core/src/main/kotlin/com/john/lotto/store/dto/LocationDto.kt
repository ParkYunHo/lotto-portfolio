package com.john.lotto.store.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.querydsl.core.annotations.QueryProjection

/**
 * @author yoonho
 * @since 2023.07.18
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LocationDto @QueryProjection constructor(
    val address1: String? = "",
    val address2: String? = "",
)
