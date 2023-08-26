package com.john.lotto.member

import com.john.lotto.entity.personal.QMember
import com.john.lotto.member.dto.MemberDto
import com.john.lotto.member.dto.QMemberDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.07.19
 */
@Repository
class MemberRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val memberEntity = QMember.member!!

    /**
     * 회원정보 등록
     *
     * @param input [MemberDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.07.19
     */
    @Transactional
    fun insertMember(input: MemberDto): Long =
        queryFactory
            .insert(memberEntity)
            .columns(
                memberEntity.userId,
                memberEntity.email,
                memberEntity.nickname,
                memberEntity.updatedAt,
                memberEntity.createdAt,
            )
            .values(
                input.userId,
                input.email,
                input.nickname,
                input.updatedAt,
                input.createdAt,
            )
            .execute()

    /**
     * 회원정보 수정
     *
     * @param input [MemberDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.08.26
     */
    @Transactional
    fun updateMember(input: MemberDto): Long =
        queryFactory
            .update(memberEntity)
            .set(memberEntity.email, input.email)
            .set(memberEntity.nickname, input.nickname)
            .where(memberEntity.userId.eq(input.userId))
            .execute()


    /**
     * 회원정보 조회
     *
     * @param userId [String]
     * @return [MemberDto]?
     * @author yoonho
     * @since 2023.07.19
     */
    @Transactional(readOnly = true)
    fun findMember(userId: String): MemberDto? =
        queryFactory
            .select(
                QMemberDto(
                    memberEntity.userId,
                    memberEntity.email,
                    memberEntity.nickname,
                    memberEntity.updatedAt,
                    memberEntity.createdAt
                )
            )
            .from(memberEntity)
            .where(memberEntity.userId.eq(userId))
            .fetchFirst()

    /**
     * 회원정보 삭제
     *
     * @param userId [String]
     * @return [Long]
     * @author yoonho
     * @since 2023.07.19
     */
    @Transactional
    fun deleteMember(userId: String): Long =
        queryFactory
            .delete(memberEntity)
            .where(memberEntity.userId.eq(userId))
            .execute()
}