package com.john.lotto.member.application.port.`in`

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
     * @return [Mono]<[String]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun delete(userId: String): Mono<String>
}