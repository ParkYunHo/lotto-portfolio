package com.john.lotto.statics.application.port.`in`

import com.john.lotto.statics.application.dto.StaticsAmountInfo
import com.john.lotto.statics.application.dto.StaticsInfo
import com.john.lotto.statics.dto.StaticsDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.12
 */
interface StaticsUseCase {
    /**
     * 월별/연도별 당첨번호 조회
     *
     * @param startDtStr [String]
     * @param endDtStr [String]
     * @param sortType [String]
     * @param sortOption [String]
     * @return [Flux]<[StaticsInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findPeriod(startDtStr: String, endDtStr: String, sortType: String, sortOption: String): Flux<StaticsInfo>

    /**
     * 회차별 당첨번호 조회
     *
     * @param startDrwtNo [String]
     * @param endDrwtNo [String]
     * @param sortType [String]
     * @param sortOption [String]
     * @return [Flux]<[StaticsInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findDrwtNo(startDrwtNo: String, endDrwtNo: String, sortType: String, sortOption: String): Flux<StaticsInfo>

    /**
     * 당첨금액별 당첨번호 조회
     *
     * @param startRank [String]
     * @param size [String]
     * @param rankSortOption [String]
     * @param sortType [String]
     * @param sortOption [String]
     * @return [Flux]<[StaticsInfo]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findWinAmount(startRank: String, size: String, rankSortOption: String, sortType: String, sortOption: String): Flux<StaticsInfo>


    /**
     * 당첨금액 순위별 당첨정보
     *
     * @param size [String]
     * @param sortOption [String]
     * @return [Flux]<[StaticsAmountInfo]>
     * @author yoonho
     * @since 2023.09.06
     */
    fun findWinAmountDetail(size: String, sortOption: String): Flux<StaticsAmountInfo>
}