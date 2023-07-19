package com.john.lotto.member.application.port.`in`

import com.john.lotto.member.dto.MemberDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.19
 */
interface FindMemberUseCase {

    /**
     * 사용자 조회
     *
     * @param userId [String]
     * @return [Mono]<[MemberDto]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun search(userId: String): Mono<MemberDto>
}