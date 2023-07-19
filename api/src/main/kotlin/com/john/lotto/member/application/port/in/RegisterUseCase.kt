package com.john.lotto.member.application.port.`in`

import com.john.lotto.member.adapter.`in`.web.dto.MemberInput
import com.john.lotto.member.dto.MemberDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.19
 */
interface RegisterUseCase {

    /**
     * 사용자 등록
     *
     * @param userId [String]
     * @param input [MemberInput]
     * @return [Mono]<[MemberDto]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun register(userId: String, input: MemberInput): Mono<MemberDto>
}