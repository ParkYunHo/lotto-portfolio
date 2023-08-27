package com.john.lotto.scrap.adatper.`in`.web.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.john.lotto.common.exception.BadRequestException
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable


/**
 * @author yoonho
 * @since 2023.07.23
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ScrapInput(
    @Schema(description = "판매점 고유ID", example = "1111")
    val storeId: String? = ""
): Serializable {

    fun validate(): ScrapInput {
        if(this.storeId.isNullOrEmpty()) {
            throw BadRequestException("판매점정보가 정보가 누락되었습니다.")
        }

        return this
    }
}
