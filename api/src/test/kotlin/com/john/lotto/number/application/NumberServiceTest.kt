package com.john.lotto.number.application

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.john.lotto.ApiApplicationTests
import com.john.lotto.common.dto.JwtTokenInfo
import com.john.lotto.common.utils.CipherUtils
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(classes = [ApiApplicationTests::class])
@ActiveProfiles("api-test")
class NumberServiceTest(
    private val numberService: NumberService,
    private val om: ObjectMapper
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

    Given("TEST") {
        val t = "eyJhdWQiOiJmYTUwOTU4YzdiMGVmZjljZWJiYzM1ODE1ODY2ZjM3OCIsInN1YiI6IjI4OTk5NzM2NjMiLCJhdXRoX3RpbWUiOjE2ODkwNzg4MTAsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLrsJXsnKTtmLgiLCJleHAiOjE2ODkxMDA0MTAsImlhdCI6MTY4OTA3ODgxMH0"

        val payload = om.readValue(
            String(Base64.getUrlDecoder().decode(t)),
            object: TypeReference<JwtTokenInfo.Payload>() {}
        )


        val instant = Instant.ofEpochSecond(payload.exp.toLong())
//        val date = Date.from(instant)

        val date = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId())

        log.info(" >>> payload: $payload, exp: $date")

    }

    Given("TEST2") {
        val t = "kakao:123456"

        val t2 = CipherUtils.encode(t)
        val t3 = CipherUtils.validate(t ,t2)
        val t4 = CipherUtils.validate("google:123456" ,t2)

        log.info(" >>> encode: $t2, validate: $t3, validate_fail: $t4")
    }
})