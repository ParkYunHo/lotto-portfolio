package com.john.lotto.store

import com.john.lotto.entity.QLottoStore
import com.john.lotto.store.dto.LottoStoreDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.06.28
 */
@Repository
class StoreRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val lottoStore = QLottoStore.lottoStore!!

    /**
     * 로또판매점 저장
     *
     * @param input [LottoStoreDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.06.28
     */
    @Transactional
    fun insertLottoStore(input: LottoStoreDto): Long =
        queryFactory
            .insert(lottoStore)
            .columns(
                lottoStore.latitude,
                lottoStore.longitude,
                lottoStore.bplclocplc1,
                lottoStore.bplclocplc2,
                lottoStore.bplclocplc3,
                lottoStore.bplclocplc4,
                lottoStore.bplcdorodtladres,
                lottoStore.rtlrstrtelno,
                lottoStore.firmnm
            )
            .values(
                input.latitude,
                input.longitude,
                input.bplclocplc1,
                input.bplclocplc2,
                input.bplclocplc3,
                input.bplclocplc4,
                input.bplcdorodtladres,
                input.rtlrstrtelno,
                input.firmnm
            )
            .execute()
}