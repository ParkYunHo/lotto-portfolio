package com.john.lotto.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.03
 */
@Entity
@Table(catalog = "LOTTO", name = "LOTTO_DRWT_STORE_TB")
class LottoDrwtStore(
    @Id
    @Column(name = "DRWT_NO", nullable = false)
    @Comment("로또 회차")
    val drwtNo: Long,
    @Id
    @Column(name = "DRWT_ORDER", nullable = false)
    @Comment("로또 당첨판매점 순서")
    val drwtOrder: Long,
    @Id
    @Column(name = "DRWT_RANK")
    @Comment("당첨 등수구분(1등,2등)")
    val drwtRank: Int? = -1,

    @Column(name = "RTLRID")
    @Comment("판매점 ID")
    val rtlrid: String? = "",
    @Column(name = "FIRMNM")
    @Comment("상호명")
    val firmnm: String? = "",
    @Column(name = "BPLCDORODTLADRES")
    @Comment("도로명 주소")
    val bplcdorodtladres: String? = "",
    @Column(name = "DRWT_TYPE")
    @Comment("당첨구분(자동,수동,반자동)")
    val drwtType: String? = "",

    @Column(name = "UPDATED_AT")
    @Comment("업데이트 일자")
    val updatedAt: LocalDateTime,
    @Column(name = "CREATED_AT", nullable = false)
    @Comment("생성 일자")
    val createdAt: LocalDateTime,
)