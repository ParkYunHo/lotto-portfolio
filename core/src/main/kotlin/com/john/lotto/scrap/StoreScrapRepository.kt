package com.john.lotto.scrap

import com.john.lotto.entity.personal.QStoreScrap
import com.john.lotto.scrap.dto.QStoreScrapDto
import com.john.lotto.scrap.dto.StoreScrapDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.07.23
 */
@Repository
class StoreScrapRepository(
    @Qualifier("lottoQueryFactory")
    private val queryFactory: JPAQueryFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val storeScrap = QStoreScrap.storeScrap!!

    /**
     * 판매점스크랩 저장
     *
     * @param input [StoreScrapDto]
     * @return [Long]
     * @author yoonho
     * @since 2023.07.23
     */
    @Transactional
    fun insertStoreScrap(input: StoreScrapDto): Long =
        queryFactory
            .insert(storeScrap)
            .columns(
                storeScrap.userId,
                storeScrap.rtlrid,
                storeScrap.updatedAt,
                storeScrap.createdAt
            )
            .values(
                input.userId,
                input.rtlrid,
                input.updatedAt,
                input.createdAt,
            )
            .execute()

    /**
     * 판매점스크랩 삭제
     *
     * @param userId [String]
     * @param rtlrid [String]
     * @return [Long]
     * @author yoonho
     * @since 2023.07.23
     */
    @Transactional
    fun deleteStoreScrap(userId: String, rtlrid: String): Long =
        queryFactory
            .delete(storeScrap)
            .where(
                storeScrap.userId.eq(userId),
                storeScrap.rtlrid.eq(rtlrid)
            )
            .execute()

    /**
     * 판매점스크랩 리스트 조회
     *
     * @param userId [String]
     * @return [List]<[StoreScrapDto]>
     * @author yoonho
     * @since 2023.07.23
     */
    @Transactional(readOnly = true)
    fun findStoreScrapList(userId: String): List<StoreScrapDto> =
        queryFactory
            .select(
                QStoreScrapDto(
                    storeScrap.userId,
                    storeScrap.rtlrid,
                    storeScrap.updatedAt,
                    storeScrap.createdAt
                )
            )
            .from(storeScrap)
            .where(
                storeScrap.userId.eq(userId)
            )
            .fetch()

    /**
     * 판매점스크랩 조회
     *
     * @param userId [String]
     * @return [StoreScrapDto]
     * @author yoonho
     * @since 2023.07.23
     */
    @Transactional(readOnly = true)
    fun findStoreScrap(userId: String, rtlrid: String): StoreScrapDto? =
        queryFactory
            .select(
                QStoreScrapDto(
                    storeScrap.userId,
                    storeScrap.rtlrid,
                    storeScrap.updatedAt,
                    storeScrap.createdAt
                )
            )
            .from(storeScrap)
            .where(
                storeScrap.userId.eq(userId),
                storeScrap.rtlrid.eq(rtlrid)
            )
            .fetchFirst()
}