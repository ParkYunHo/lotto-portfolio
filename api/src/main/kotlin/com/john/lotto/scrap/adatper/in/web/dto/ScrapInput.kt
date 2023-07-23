package com.john.lotto.scrap.adatper.`in`.web.dto

import com.john.lotto.common.exception.BadRequestException
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable


/**
 * @author yoonho
 * @since 2023.07.23
 */
data class ScrapInput(
    @Schema(description = "rtlrid", example = "1111")
    val rtlrid: String? = ""
): Serializable {

    fun validate(): ScrapInput {
        if(this.rtlrid.isNullOrEmpty()) {
            throw BadRequestException("판매점정보가 정보가 누락되었습니다.")
        }

        return this
    }
}
