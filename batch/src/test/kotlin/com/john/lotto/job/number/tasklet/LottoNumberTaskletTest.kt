package com.john.lotto.job.number.tasklet

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.john.lotto.BatchApplicationTests
import com.john.lotto.rest.dto.TotalLottoNumberDto
import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [BatchApplicationTests::class])
@ActiveProfiles("batch-test")
class LottoNumberTaskletTest(
    private val om: ObjectMapper
): BehaviorSpec({
    val log = LoggerFactory.getLogger(this::class.java)

    Given("TEST") {
        val t = "{\"totSellamnt\":107352123000,\"returnValue\":\"success\",\"drwNoDate\":\"2023-06-24\",\"firstWinamnt\":2345227603,\"drwtNo6\":38,\"drwtNo4\":30,\"firstPrzwnerCo\":11,\"drwtNo5\":32,\"bnusNo\":15,\"firstAccumamnt\":25797503633,\"drwNo\":1073,\"drwtNo2\":18,\"drwtNo3\":28,\"drwtNo1\":6}"
//        val t = "{\"drwtNo6\":38,\"drwtNo4\":30,\"drwtNo5\":32,\"bnusNo\":15,\"drwNo\":1073,\"drwtNo2\":18,\"drwtNo3\":28,\"drwtNo1\":6}"

//        val t = "{\"drwNo\": 1073}"
//        val t = "{'drwtNo': 1073}"

        val a = JsonParser.parseString(t)

        val result = Gson().fromJson(a, TotalLottoNumberDto::class.java)
//        om.enable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
//        om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
//        val result = om.convertValue(a, object: TypeReference<TotalLottoNumberDto>() {})

        log.info(" >>> result: $result")
    }
})