package com.john.lotto.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Entity
@Table(catalog = "LOTTO", name = "LOTTO_SELLER_TB")
class LottoSeller(
    @Id
    @Column(name = "LATITUDE", nullable = false)
    @Comment("판매점 위도")
    val latitude: Float,
    @Id
    @Column(name = "LONGITUDE", nullable = false)
    @Comment("판매점 경도")
    val longitude: Float,

    @Column(name = "BPLCLOCPLC1", nullable = false)
    @Comment("행정구역(시)")
    val bplclocplc1: String,
    @Column(name = "BPLCLOCPLC2", nullable = false)
    @Comment("행정구역(구)")
    val bplclocplc2: String,
    @Column(name = "BPLCLOCPLC3")
    @Comment("행정구역(동)")
    val bplclocplc3: String? = "",
    @Column(name = "BPLCLOCPLC4")
    @Comment("행정구역(상세주소)")
    val bplclocplc4: String? = "",

    @Column(name = "BPLCDORODTLADRES")
    @Comment("도로명 주소")
    val bplcdorodtladres: String? = "",
    @Column(name = "BPLCLOCPLCDTLADRES")
    @Comment("지번 주소")
    val bplclocplcdtladres: String? = "",

    @Column(name = "RTLRSTRTELNO")
    @Comment("전화번호")
    val rtlrstrtelno: String? = "",

    @Column(name = "FIRMNM", nullable = false)
    @Comment("상호명")
    val firmnm: String,
)