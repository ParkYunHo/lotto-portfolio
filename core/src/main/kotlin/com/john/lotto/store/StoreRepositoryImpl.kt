package com.john.lotto.store

import com.john.lotto.entity.lotto.QLottoStore
import com.john.lotto.store.dto.LocationDto
import com.john.lotto.store.dto.LottoStoreDto
import com.john.lotto.store.dto.QLocationDto
import com.john.lotto.store.dto.QLottoStoreDto
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

    /**
     * 로또 판매점 조회
     *
     * @param location [String]
     * @param subLocation [String]
     * @return [List]<[LottoStoreDto]>
     * @author yoonho
     * @since 2023.07.18
     */
    @Transactional(readOnly = true)
    fun findLottoStore(location: String, subLocation: String): List<LottoStoreDto> =
        queryFactory
            .select(
                QLottoStoreDto(
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
            )
            .from(lottoStore)
            .where(
                lottoStore.bplclocplc1.like(location),
                lottoStore.bplclocplc2.like(subLocation)
            )
            .fetch()

    /**
     * 로또 판매점 조회 (rtlrid 조회)
     *
     * @param storeIds [List]<[String]>
     * @return [List]<[LottoStoreDto]>
     * @author yoonho
     * @since 2023.09.05
     */
    @Transactional(readOnly = true)
    fun findLottoStoreByStoreId(storeIds: List<String>): List<LottoStoreDto> =
        queryFactory
            .select(
                QLottoStoreDto(
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
            )
            .from(lottoStore)
            .where(
                lottoStore.rtlrid.`in`(storeIds)
            )
            .fetch()

    /**
     * 판매점 위치정보 조회
     *
     * @return [List]<[LocationDto]>
     * @author yoonho
     * @since 2023.07.18
     */
    @Transactional(readOnly = true)
    fun findLocation(): List<LocationDto> =
        queryFactory
            .select(
                QLocationDto(
                    lottoStore.bplclocplc1,
                    lottoStore.bplclocplc2,
                )
            )
            .from(lottoStore)
            .groupBy(
                lottoStore.bplclocplc1,
                lottoStore.bplclocplc2
            )
            .fetch()
}