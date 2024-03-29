package com.john.lotto.member.application

import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.member.MemberRepository
import com.john.lotto.member.adapter.`in`.web.dto.MemberInput
import com.john.lotto.member.adapter.`in`.web.dto.MemberResult
import com.john.lotto.member.application.port.`in`.DeleteMemberUseCase
import com.john.lotto.member.application.port.`in`.FindMemberUseCase
import com.john.lotto.member.application.port.`in`.RegisterUseCase
import com.john.lotto.member.application.port.`in`.UpdateUseCase
import com.john.lotto.member.dto.MemberDto
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.19
 */
@Service
class MemberService(
    private val memberRepository: MemberRepository
): RegisterUseCase, FindMemberUseCase, DeleteMemberUseCase, UpdateUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 사용자 등록
     *
     * @param userId [String]
     * @param input [MemberInput]
     * @return [Mono]<[MemberDto]>
     * @author yoonho
     * @since 2023.07.19
     */
    override fun register(userId: String, input: MemberInput): Mono<MemberDto> {
        val existsMember = memberRepository.findMember(userId = userId)
        if(existsMember != null) {
            throw BadRequestException("이미 등록된 사용자입니다")
        }

        val param = MemberDto(
            userId = userId,
            email = input.email!!,
            nickname = input.nickname!!,
            updatedAt = null,
            createdAt = LocalDateTime.now()
        )
        val result = memberRepository.insertMember(param)
        if(result <= 0) {
            throw InternalServerException("사용자 등록에 실패하였습니다.")
        }

        return Mono.just(param)
    }

    /**
     * 사용자 정보수정
     *
     * @param userId [String]
     * @param input [MemberInput]
     * @return [Mono]<[MemberDto]>
     * @author yoonho
     * @since 2023.08.25
     */
    override fun update(userId: String, input: MemberInput): Mono<MemberDto> {
        val userInfo = memberRepository.findMember(userId = userId) ?: throw BadRequestException("미등록된 회원입니다 - userId: $userId")

        // 이메일 업데이트
        if(input.email.isNullOrEmpty()) {
            userInfo.email = input.email!!
        }

        // 닉네임 업데이트
        if(input.nickname.isNullOrEmpty()) {
            userInfo.nickname = input.nickname!!
        }

        val result = memberRepository.updateMember(userInfo)
        if(result <= 0) {
            throw InternalServerException("사용자 정보수정에 실패하였습니다.")
        }

        return Mono.just(userInfo)
    }

    /**
     * 사용자 조회
     *
     * @param userId [String]
     * @return [Mono]<[MemberDto]>
     * @author yoonho
     * @since 2023.07.19
     */
    @Cacheable(cacheNames = ["member.common"], key = "#userId", unless = "#result == null")
    override fun search(userId: String): Mono<MemberDto> {
        val userInfo = memberRepository.findMember(userId = userId) ?: throw BadRequestException("미등록된 회원입니다 - userId: $userId")
        return Mono.just(userInfo)
    }

    /**
     * 사용자 탈퇴
     *
     * @param userId [String]
     * @return [Mono]<[MemberResult]>
     * @author yoonho
     * @since 2023.08.27
     */
    override fun delete(userId: String): Mono<MemberResult> {
        val result = memberRepository.deleteMember(userId = userId)
        if(result <= 0) {
            throw BadRequestException("미등록된 회원입니다 - userId: $userId")
        }

        return Mono.just(MemberResult(userId = userId))
    }
}