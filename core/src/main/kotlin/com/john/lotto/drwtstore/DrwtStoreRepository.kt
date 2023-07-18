package com.john.lotto.drwtstore

import com.john.lotto.drwtstore.dto.DrwtStoreDto
import com.john.lotto.drwtstore.dto.QDrwtStoreDto
import com.john.lotto.entity.QLottoDrwtStore
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.07.03
 */
@Repository
class DrwtStoreRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val lottoDrwtStore = QLottoDrwtStore.lottoDrwtStore!!

    /**
     * 당첨판매점 저장
     *
     * @param input [DrwtStoreDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.07.18
     */
    @Transactional
    fun insertLottoDrwtStore(input: DrwtStoreDto): Long =
        queryFactory
            .insert(lottoDrwtStore)
            .columns(
                lottoDrwtStore.drwtNo,
                lottoDrwtStore.drwtOrder,
                lottoDrwtStore.drwtRank,
                lottoDrwtStore.rtlrid,
                lottoDrwtStore.firmnm,
                lottoDrwtStore.bplcdorodtladres,
                lottoDrwtStore.drwtType,
                lottoDrwtStore.updatedAt,
                lottoDrwtStore.createdAt
            )
            .values(
                input.drwtNo,
                input.drwtOrder,
                input.drwtRank,
                input.rtlrid,
                input.firmnm,
                input.bplcdorodtladres,
                input.drwtType,
                null,
                input.createdAt
            )
            .execute()

    /**
     * 당첨판매점 조회
     *
     * @param ids [List]<[String]>
     * @return [List]<[DrwtStoreDto]>
     * @author yoonho
     * @since 2023.07.18
     */
    @Transactional(readOnly = true)
    fun findLottoDrwtStore(ids: List<String>): List<DrwtStoreDto> =
        queryFactory
            .select(
                QDrwtStoreDto(
                    lottoDrwtStore.drwtNo,
                    lottoDrwtStore.drwtOrder,
                    lottoDrwtStore.drwtRank,
                    lottoDrwtStore.rtlrid,
                    lottoDrwtStore.firmnm,
                    lottoDrwtStore.bplcdorodtladres,
                    lottoDrwtStore.drwtType,
                    lottoDrwtStore.updatedAt,
                    lottoDrwtStore.createdAt
                )
            )
            .from(lottoDrwtStore)
            .where(
                lottoDrwtStore.rtlrid.`in`(ids)
            )
            .fetch()
}