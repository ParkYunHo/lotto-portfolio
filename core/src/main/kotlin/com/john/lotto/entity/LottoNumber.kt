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
 * @since 2023.06.21
 */
@Entity
@Table(catalog = "LOTTO", name = "LOTTO_NUMBER_TB")
class LottoNumber(
    @Id
    @Column(name = "DRWT_NO", nullable = false)
    @Comment("로또 회차")
    val drwtNo: Long,

    @Column(name = "DRWT_NO_1", nullable = false)
    @Comment("로또 당첨번호 1번")
    val drwtNo1: Long,
    @Column(name = "DRWT_NO_2", nullable = false)
    @Comment("로또 당첨번호 2번")
    val drwtNo2: Long,
    @Column(name = "DRWT_NO_3", nullable = false)
    @Comment("로또 당첨번호 3번")
    val drwtNo3: Long,
    @Column(name = "DRWT_NO_4", nullable = false)
    @Comment("로또 당첨번호 4번")
    val drwtNo4: Long,
    @Column(name = "DRWT_NO_5", nullable = false)
    @Comment("로또 당첨번호 5번")
    val drwtNo5: Long,
    @Column(name = "DRWT_NO_6", nullable = false)
    @Comment("로또 당첨번호 6번")
    val drwtNo6: Long,
    @Column(name = "BNUS_NO", nullable = false)
    @Comment("로또 당첨번호 보너스번호")
    val bnusNo: Long,

    @Column(name = "UPDATED_AT")
    @Comment("업데이트 일자")
    val updatedAt: LocalDateTime? = null,
    @Column(name = "CREATED_AT", nullable = false)
    @Comment("생성 일자")
    val createdAt: LocalDateTime,
) {
}