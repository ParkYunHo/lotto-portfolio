package com.john.lotto.entity.personal

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.19
 */
@Entity
@Table(catalog = "LOTTO", name = "LOTTO_MEMBER_TB")
class Member(
    @Id
    @Column(name = "USER_ID", nullable = false)
    @Comment("사용자 ID")
    val userId: String,

    @Column(name = "EMAIL", nullable = false)
    @Comment("사용자 이메일")
    val email: String,
    @Column(name = "NICK_NAME", nullable = false)
    @Comment("사용자 닉네임")
    val nickname: String,

    @Column(name = "UPDATED_AT")
    @Comment("업데이트 일자")
    val updatedAt: LocalDateTime,
    @Column(name = "CREATED_AT", nullable = false)
    @Comment("생성 일자")
    val createdAt: LocalDateTime,
)