package com.john.lotto.statics

import com.john.lotto.CoreApplicationTests
import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

/**
 * @author yoonho
 * @since 2023.07.07
 */
@SpringBootTest(classes = [CoreApplicationTests::class])
@ActiveProfiles("core-test")
class StaticsRepositoryTest(
    private val staticsRepository: StaticsRepository
): BehaviorSpec({
    val log = LoggerFactory.getLogger(this::class.java)

    Given("먼저, 월별/연도별 당첨번호조회를 위한 파라미터가 세팅되어 있다.") {
        val startDate = LocalDate.of(2023,4,1)
        val endDate = LocalDate.of(2023,5,1)

        When("만약, 월별/연도별 당첨번호조회를 요청하면") {
            val result = staticsRepository.findPeriodStatics(startDt = startDate, endDt = endDate)

            Then("그러면, 월별/연도별 당첨번호조회 결과를 확인한다") {
                log.info(" >>> [findPeriodStatics] result: $result")
            }
        }
    }

    Given("먼저, 회차별 당첨번호조회를 위한 파라미터가 세팅되어 있다.") {
        val startDrwtNo = 1060L
        val endDrwtNo = 1070L

        When("만약, 회차별 당첨번호조회를 요청하면") {
            val result = staticsRepository.findDrwtNoStatics(startDrwtNo = startDrwtNo, endDrwtNo = endDrwtNo)

            Then("그러면, 회차별 당첨번호조회 결과를 확인한다") {
                log.info(" >>> [findDrwtNoStatics] result: $result")
            }
        }
    }

    Given("먼저, 당첨금액별 당첨번호조회를 위한 파라미터가 세팅되어 있다.") {
        val startRank = 0L
        val size = 3L
        val isDesc = true

        When("만약, 당첨금액별 당첨번호조회를 요청하면") {
            val result = staticsRepository.findWinAmountStatics(startRank = startRank, size = size, isDesc = isDesc)

            Then("그러면, 당첨금액별 당첨번호조회 결과를 확인한다") {
                log.info(" >>> [findWinAmountStatics] result: $result")
            }
        }
    }
})