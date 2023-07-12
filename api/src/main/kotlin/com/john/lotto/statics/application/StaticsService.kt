package com.john.lotto.statics.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.common.exception.InternalServerException
import com.john.lotto.common.utils.ObjectMapperUtils
import com.john.lotto.statics.StaticsRepository
import com.john.lotto.statics.application.port.`in`.StaticsPeriodUseCase
import com.john.lotto.statics.dto.StaticsDto
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author yoonho
 * @since 2023.07.12
 */
@Service
class StaticsService(
    private val staticsRepository: StaticsRepository,
    private val objectMapper: ObjectMapper
): StaticsPeriodUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 월별/연도별 당첨번호 조회
     *
     * @param startDtStr [String]
     * @param endDtStr [String]
     * @return [Mono]<[StaticsDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    @Cacheable(cacheNames = ["statics.common"], key = "#startDtStr + ':' + #endDtStr", unless = "#result == null")
    override fun findPeriod(startDtStr: String, endDtStr: String): Mono<StaticsDto> {
        if(startDtStr.length != 6 || endDtStr.length != 6) {
            return Mono.error(BadRequestException("날짜형식이 잘못되었습니다 - startDt: $startDtStr, endDt: $endDtStr"))
        }

        return try {
            val startDt = LocalDate.parse(startDtStr + "01", DateTimeFormatter.ofPattern("yyyyMMdd"))
            val endDt = LocalDate.parse(endDtStr + "31", DateTimeFormatter.ofPattern("yyyyMMdd"))
            val result = staticsRepository.findPeriodStatics(startDt = startDt, endDt = endDt)

            log.info(" >>> [findPeriod] result: $result")
            Mono.just(ObjectMapperUtils.decode(source = result, classType = StaticsDto::class.java))
        }catch (e: Exception) {
            log.error(" >>> [findPeriod] Exception occurs - message: ${e.message}")
            Mono.error(InternalServerException("월별/연도별 당첨번호 조회에 실패하였습니다."))
        }
    }

    /**
     * 회차별 당첨번호 조회
     *
     * @param startDrwtNo [String]
     * @param endDrwtNo [String]
     * @return [Mono]<[StaticsDto]>
     * @author yoonho
     * @since 2023.07.12
     */
    @Cacheable(cacheNames = ["statics.common"], key = "#startDrwtNo + ':' + #endDrwtNo", unless = "#result == null")
    override fun findDrwtNo(startDrwtNo: String, endDrwtNo: String): Mono<StaticsDto> =
        try {
            val result = staticsRepository.findDrwtNoStatics(startDrwtNo = startDrwtNo.toLong(), endDrwtNo = endDrwtNo.toLong())

            log.info(" >>> [findDrwtNo] result: $result")
            Mono.just(ObjectMapperUtils.decode(source = result, classType = StaticsDto::class.java))
        }catch (e: Exception) {
            log.error(" >>> [findDrwtNo] Exception occurs - message: ${e.message}")
            Mono.error(InternalServerException("회차별 당첨번호 조회에 실패하였습니다."))
        }

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
    @Cacheable(cacheNames = ["statics.common"], key = "#startRank + ':' + #size + ':' + #isDesc", unless = "#result == null")
    override fun findWinAmount(startRank: String, size: String, isDesc: String): Mono<StaticsDto> =
        try {
            val checkDesc = when(isDesc) {
                "0" -> false
                "1" -> true
                else -> true
            }
            val result = staticsRepository.findWinAmountStatics(startRank = startRank.toLong(), size = size.toLong(), isDesc = checkDesc)

            log.info(" >>> [findWinAmount] result: $result")
            Mono.just(ObjectMapperUtils.decode(source = result, classType = StaticsDto::class.java))
        }catch (e: Exception) {
            log.error(" >>> [findWinAmount] Exception occurs - message: ${e.message}")
            Mono.error(InternalServerException("당첨금액별 당첨번호 조회에 실패하였습니다."))
        }
}