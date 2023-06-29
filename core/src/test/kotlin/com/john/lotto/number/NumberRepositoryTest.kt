package com.john.lotto.number

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.lotto.CoreApplicationTests
import com.john.lotto.number.dto.LottoNumberDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import javax.sql.DataSource

/**
 * @author yoonho
 * @since 2023.06.22
 */
@SpringBootTest(classes = [CoreApplicationTests::class])
@ActiveProfiles("core-test")
class NumberRepositoryTest(
    private val numberRepository: NumberRepository,

    private val om: ObjectMapper
): BehaviorSpec({
    val log = LoggerFactory.getLogger(this::class.java)

    Given("먼저, 특정 로또번호 조회를 위한 파라미터가 세팅되어 있다.") {
        val drwtNo = 1072L

        When("만약, 특정 로또번호 조회를 요청하면") {
            val result = numberRepository.findLottoNumber(drwtNo = drwtNo)

            Then("그러면, 특정 로또번호 조회 결과를 확인한다.") {
                log.info(" >>> [findLottoNumber] result: $result")
                result shouldNotBe null
            }
        }
    }

    Given("먼저, 마지막 로또회차정보 조회를 위한 파라미터가 세팅되어 있다.") {

        When("만약, 마지막 로또회차정보 조회를 요청하면") {
            val result = numberRepository.findLastDrwtNo()

            Then("그러면, 마지막 로또회차정보 조회 결과를 확인한다.") {
                log.info(" >>> [findLastDrwtNo] result: $result")
                result shouldNotBe null
            }
        }
    }

    Given("먼저, 로또번호 저장을 위한 파라미터가 세팅되어 있다.") {
        val input = LottoNumberDto(
            drwtNo = 1073,
            drwtNo1 = 1L,
            drwtNo2 = 1L,
            drwtNo3 = 1L,
            drwtNo4 = 1L,
            drwtNo5 = 1L,
            drwtNo6 = 1L,
            bnusNo = 1L,
        )

        When("만약, 로또번호 저장을 요청하면") {
            val result = numberRepository.insertLottoNumber(input = input)

            Then("그러면, 로또번호 저장 결과를 확인한다.") {
                log.info(" >>> [insertLottoNumber] result: $result")
                result shouldNotBe null
            }
        }
    }
})