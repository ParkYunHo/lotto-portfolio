package com.john.lotto.scrap.adatper.`in`.web.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * @author yoonho
 * @since 2023.08.27
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ScrapResult(
    val userId: String? = "",
    val storeId: String? = ""
)