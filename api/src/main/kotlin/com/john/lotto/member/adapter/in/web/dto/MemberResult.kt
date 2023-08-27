package com.john.lotto.member.adapter.`in`.web.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * @author yoonho
 * @since 2023.08.27
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MemberResult(
    val userId: String? = ""
)