package com.john.lotto.member.adapter.`in`.web.dto

import com.john.lotto.common.exception.BadRequestException
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable


/**
 * @author yoonho
 * @since 2023.07.19
 */
data class MemberInput(
    @Schema(description = "email", example = "lotto@email.com")
    val email: String? = "",
    @Schema(description = "닉네임", example = "admin1")
    val nickName: String? = ""
): Serializable {

    fun validate(): MemberInput {
        if(this.email.isNullOrEmpty()) {
            throw BadRequestException("이메일 정보가 누락되었습니다.")
        }

        if(this.nickName.isNullOrEmpty()) {
            throw BadRequestException("닉네임 정보가 누락되었습니다")
        }

        return this
    }
}
