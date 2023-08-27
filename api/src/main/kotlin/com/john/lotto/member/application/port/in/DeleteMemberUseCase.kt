package com.john.lotto.member.application.port.`in`

import com.john.lotto.member.adapter.`in`.web.dto.MemberResult
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.19
 */
interface DeleteMemberUseCase {

    /**
     * 사용자 탈퇴
     *
     * @param userId [String]
     * @return [Mono]<[MemberResult]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun delete(userId: String): Mono<MemberResult>
}