package com.john.lotto.number.application

import com.john.lotto.ApiApplicationTests
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [ApiApplicationTests::class])
@ActiveProfiles("api-test")
class NumberServiceTest(
    private val numberService: NumberService
): BehaviorSpec({
    val log = LoggerFactory.getLogger(this::class.java)

    Given("먼저, 특정 로또번호 조회를 위한 파라미터가 세팅되어 있다.") {
        val drwtNo = 1072L

        When("만약, 특정 로또번호 조회를 요청하면") {
            val result = numberService.findLottoNumber(drwtNo = drwtNo).block()

            Then("그러면, 특정 로또번호 조회 결과를 확인한다.") {
                log.info(" >>> [findLottoNumber] result: $result")
                result shouldNotBe null
            }
        }
    }
})