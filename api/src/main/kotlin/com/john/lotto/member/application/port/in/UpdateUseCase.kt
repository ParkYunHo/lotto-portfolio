package com.john.lotto.member.application.port.`in`

import com.john.lotto.member.adapter.`in`.web.dto.MemberInput
import com.john.lotto.member.dto.MemberDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.08.25
 */
interface UpdateUseCase {

    /**
     * 사용자 정보수정
     *
     * @param userId [String]
     * @param input [MemberInput]
     * @return [Mono]<[MemberDto]>
     * @author yoonho
     * @since 2023.08.25
     */
    fun update(userId: String, input: MemberInput): Mono<MemberDto>
}