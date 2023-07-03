package com.john.lotto.store

import com.john.lotto.entity.QLottoStore
import com.john.lotto.store.dto.LottoStoreDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.06.28
 */
@Repository
class StoreRepositoryImpl(
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
                            lottoStore.rtlrid,
                            lottoStore.latitude,
                            lottoStore.longitude,
                            lottoStore.bplclocplc1,
                            lottoStore.bplclocplc2,
                            lottoStore.bplclocplc3,
                            lottoStore.bplclocplc4,
                            lottoStore.bplcdorodtladres,
                            lottoStore.bplclocplcdtladres,
                            lottoStore.rtlrstrtelno,
                            lottoStore.firmnm,
                            lottoStore.updatedAt,
                            lottoStore.createdAt
                    )
                    .values(
                            input.rtlrid,
                            input.latitude,
                            input.longitude,
                            input.bplclocplc1,
                            input.bplclocplc2,
                            input.bplclocplc3,
                            input.bplclocplc4,
                            input.bplcdorodtladres,
                            input.bplclocplcdtladres,
                            input.rtlrstrtelno,
                            input.firmnm,
                            null,
                            LocalDateTime.now()
                    )
                    .execute()

    /**
     * 로또판매점 전체삭제
     *
     * @return [Long]
     * @author yoonho
     * @since 2023.07.02
     */
    @Transactional
    fun deleteAllLottoStore(): Long =
            queryFactory
                    .delete(lottoStore)
                    .execute()
}