package com.john.lotto.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.06.28
 */
@Entity
@Table(catalog = "LOTTO", name = "LOTTO_WIN_AMOUNT_TB")
class LottoWinAmount(
    @Id
    @Column(name = "DRWT_NO", nullable = false)
    @Comment("로또 회차")
    val drwtNo: Long,

    @Column(name = "DRWT_DATE", nullable = false)
    @Comment("로또 추첨일자")
    val drwtDate: LocalDate,
    @Column(name = "TOT_SELL_AMNT", nullable = false)
    @Comment("총 판매금액")
    val totSellamnt: Long,
    @Column(name = "FIRST_WIN_AMNT", nullable = false)
    @Comment("1등 1게임당 당첨금액")
    val firstWinamnt: Long,
    @Column(name = "FIRST_PRZ_WNER_CO", nullable = false)
    @Comment("1등 당첨게임수")
    val firstPrzwnerCo: Long,
    @Column(name = "FIRST_ACCUM_AMNT", nullable = false)
    @Comment("1등 전체 당첨금액")
    val firstAccumamnt: Long
)