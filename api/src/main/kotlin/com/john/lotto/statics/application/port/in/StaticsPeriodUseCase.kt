package com.john.lotto.statics.application.port.`in`

import com.john.lotto.statics.dto.StaticsDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.07.12
 */
interface StaticsPeriodUseCase {
    /**
     * 월별/연도별 당첨번호 조회
     *
     * @param startDtStr [String]
     * @param endDtStr [String]
     * @return [Mono]<[StaticsDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findPeriod(startDtStr: String, endDtStr: String): Mono<StaticsDto>

    /**
     * 회차별 당첨번호 조회
     *
     * @param startDrwtNo [String]
     * @param endDrwtNo [String]
     * @return [Mono]<[StaticsDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findDrwtNo(startDrwtNo: String, endDrwtNo: String): Mono<StaticsDto>

    /**
     * 당첨금액별 당첨번호 조회
     *
     * @param startRank [String]
     * @param size [String]
     * @param isDesc [String]
     * @return [Mono]<[StaticsDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    fun findWinAmount(startRank: String, size: String, isDesc: String): Mono<StaticsDto>
}