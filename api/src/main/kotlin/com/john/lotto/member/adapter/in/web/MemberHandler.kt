package com.john.lotto.member.adapter.`in`.web

import com.john.lotto.common.dto.BaseResponse
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.filter.userId
import com.john.lotto.member.adapter.`in`.web.dto.MemberInput
import com.john.lotto.member.application.port.`in`.DeleteMemberUseCase
import com.john.lotto.member.application.port.`in`.FindMemberUseCase
import com.john.lotto.member.application.port.`in`.RegisterUseCase
import com.john.lotto.member.application.port.`in`.UpdateUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.19
 */
@Component
class MemberHandler(
    private val registerUseCase: RegisterUseCase,
    private val updateUseCase: UpdateUseCase,
    private val findMemberUseCase: FindMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 사용자 등록
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun register(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(MemberInput::class.java)
            .flatMap { return@flatMap Mono.just(it.validate()) }
            .flatMap { registerUseCase.register(userId = request.userId(), input = it) }
            .flatMap { BaseResponse().success(it) }

    /**
     * 사용자 정보수정
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.08.25
     */
    fun update(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(MemberInput::class.java)
            .flatMap { return@flatMap Mono.just(it.validate()) }
            .flatMap { updateUseCase.update(userId = request.userId(), input = it) }
            .flatMap { BaseResponse().success(it) }

    /**
     * 사용자 조회
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun search(request: ServerRequest): Mono<ServerResponse> =
        findMemberUseCase.search(request.userId())
            .flatMap { BaseResponse().success(it) }

    /**
     * 사용자 탈퇴
     *
     * @param request [ServerRequest]
     * @return [Mono]<[ServerResponse]>
     * @author yoonho
     * @since 2023.07.19
     */
    fun delete(request: ServerRequest): Mono<ServerResponse> =
        deleteMemberUseCase.delete(request.userId())
            .flatMap { BaseResponse().success(it) }
}