package com.john.lotto.statics.application

import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.statics.StaticsRepository
import com.john.lotto.statics.application.dto.StaticsInfo
import com.john.lotto.statics.application.port.`in`.StaticsUseCase
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author yoonho
 * @since 2023.07.12
 */
@Service
class StaticsService(
    private val staticsRepository: StaticsRepository
): StaticsUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 월별/연도별 당첨번호 조회
     *
     * @param startDtStr [String]
     * @param endDtStr [String]
     * @param sortType [String]
     * @param sortOption [String]
     * @return [Flux]<[StaticsInfo]>
     * @author yoonho
     * @since 2023.09.05
     */
    @Cacheable(cacheNames = ["statics.common"], key = "#startDtStr + ':' + #endDtStr + ':' + #sortType + ':' + #sortOption", unless = "#result == null")
    override fun findPeriod(startDtStr: String, endDtStr: String, sortType: String, sortOption: String): Flux<StaticsInfo> {
        if(startDtStr.length != 6 || endDtStr.length != 6) {
            return Flux.error(BadRequestException("날짜형식이 잘못되었습니다 - startDt: $startDtStr, endDt: $endDtStr"))
        }

        return try {
            val startDt = LocalDate.parse(startDtStr + "01", DateTimeFormatter.ofPattern("yyyyMMdd"))
            val endDt = LocalDate.parse(endDtStr + "31", DateTimeFormatter.ofPattern("yyyyMMdd"))
            val result = staticsRepository.findPeriodStatics(startDt = startDt, endDt = endDt)

            log.info(" >>> [findPeriod] result: $result")
            val staticsInfos: MutableList<StaticsInfo> = mutableListOf()
            for(entry: Map.Entry<String, Long> in result) {
                staticsInfos.add(StaticsInfo(no = entry.key.toInt(), count = entry.value.toInt()))
            }

            // 결과값 정렬
            this.sortResult(sortType = sortType, sortOption = sortOption, staticsInfos = staticsInfos)

            Flux.fromIterable(staticsInfos)
        }catch (e: Exception) {
            log.error(" >>> [findPeriod] Exception occurs - message: ${e.message}")
            Flux.error(InternalServerException("월별/연도별 당첨번호 조회에 실패하였습니다."))
        }
    }

    /**
     * 회차별 당첨번호 조회
     *
     * @param startDrwtNo [String]
     * @param endDrwtNo [String]
     * @param sortType [String]
     * @param sortOption [String]
     * @return [Flux]<[StaticsInfo]>
     * @author yoonho
     * @since 2023.09.05
     */
    @Cacheable(cacheNames = ["statics.common"], key = "#startDrwtNo + ':' + #endDrwtNo + ':' + #sortType + ':' + #sortOption", unless = "#result == null")
    override fun findDrwtNo(startDrwtNo: String, endDrwtNo: String, sortType: String, sortOption: String): Flux<StaticsInfo> =
        try {
            val result = staticsRepository.findDrwtNoStatics(startDrwtNo = startDrwtNo.toLong(), endDrwtNo = endDrwtNo.toLong())

            log.info(" >>> [findDrwtNo] result: $result")
            val staticsInfos: MutableList<StaticsInfo> = mutableListOf()
            for(entry: Map.Entry<String, Long> in result) {
                staticsInfos.add(StaticsInfo(no = entry.key.toInt(), count = entry.value.toInt()))
            }

            // 결과값 정렬
            this.sortResult(sortType = sortType, sortOption = sortOption, staticsInfos = staticsInfos)

            Flux.fromIterable(staticsInfos)
        }catch (e: Exception) {
            log.error(" >>> [findDrwtNo] Exception occurs - message: ${e.message}")
            Flux.error(InternalServerException("회차별 당첨번호 조회에 실패하였습니다."))
        }

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
     * @since 2023.09.05
     */
    @Cacheable(cacheNames = ["statics.common"], key = "#startRank + ':' + #size + ':' + #rankSortOption + ':' + #sortType + ':' + #sortOption", unless = "#result == null")
    override fun findWinAmount(startRank: String, size: String, rankSortOption: String, sortType: String, sortOption: String): Flux<StaticsInfo> =
        try {
            val checkDesc = when(rankSortOption) {
                "DESC" -> true
                "ASC" -> false
                else -> false
            }
            val result = staticsRepository.findWinAmountStatics(startRank = startRank.toLong(), size = size.toLong(), isDesc = checkDesc)

            log.info(" >>> [findWinAmount] result: $result")
            val staticsInfos: MutableList<StaticsInfo> = mutableListOf()
            for(entry: Map.Entry<String, Long> in result) {
                staticsInfos.add(StaticsInfo(no = entry.key.toInt(), count = entry.value.toInt()))
            }

            // 결과값 정렬
            this.sortResult(sortType = sortType, sortOption = sortOption, staticsInfos = staticsInfos)

            Flux.fromIterable(staticsInfos)
        }catch (e: Exception) {
            log.error(" >>> [findWinAmount] Exception occurs - message: ${e.message}")
            Flux.error(InternalServerException("당첨금액별 당첨번호 조회에 실패하였습니다."))
        }

    /**
     * 통계 결과값 정렬
     *
     * @param sortType [String]
     * @param sortOption [String]
     * @param staticsInfos [MutableList]<[StaticsInfo]>
     * @author yoonho
     * @since 2023.09.05
     */
    private fun sortResult(sortType: String, sortOption: String, staticsInfos: MutableList<StaticsInfo>) {
        if(sortOption.equals("DESC", true)) {
            staticsInfos.sortByDescending {
                if(sortType.equals("NO", true)) it.no
                else if(sortType.equals("COUNT", true)) it.count
                else it.no
            }
        }else if(sortOption.equals("ASC", true)) {
            staticsInfos.sortBy {
                if(sortType.equals("NO", true)) it.no
                else if(sortType.equals("COUNT", true)) it.count
                else it.no
            }
        }else {
            staticsInfos.sortByDescending {
                if(sortType.equals("NO", true)) it.no
                else if(sortType.equals("COUNT", true)) it.count
                else it.no
            }
        }
    }
}