package com.john.lotto.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.06
 */
@Entity
@Table(catalog = "LOTTO", name = "LOTTO_STATIC_NUMBER_TB")
class LottoPeriodNumberStatic(
    // TODO: 당첨번호 통계를 어떻게 낼지 확인

    @Id
    @Column(name = "PERIOD_YEAR", nullable = false)
    @Comment("로또 회차")
    val year: Long,
    @Id
    @Column(name = "PERIOD_MONTH", nullable = false)
    @Comment("로또 회차")
    val month: Long,


    @Column(name = "DRWT_NO_1", nullable = false)
    @Comment("로또 당첨숫자 1번")
    val drwtNo1: Long,
    @Column(name = "DRWT_NO_2", nullable = false)
    @Comment("로또 당첨숫자 2번")
    val drwtNo2: Long,
    @Column(name = "DRWT_NO_3", nullable = false)
    @Comment("로또 당첨숫자 3번")
    val drwtNo3: Long,
    @Column(name = "DRWT_NO_4", nullable = false)
    @Comment("로또 당첨숫자 4번")
    val drwtNo4: Long,
    @Column(name = "DRWT_NO_5", nullable = false)
    @Comment("로또 당첨숫자 5번")
    val drwtNo5: Long,
    @Column(name = "DRWT_NO_6", nullable = false)
    @Comment("로또 당첨숫자 6번")
    val drwtNo6: Long,


    @Column(name = "UPDATED_AT")
    @Comment("업데이트 일자")
    val updatedAt: LocalDateTime? = null,
    @Column(name = "CREATED_AT", nullable = false)
    @Comment("생성 일자")
    val createdAt: LocalDateTime,
) {
}