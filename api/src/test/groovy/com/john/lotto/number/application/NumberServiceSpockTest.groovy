package com.john.lotto.number.application

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

//@Slf4j
//@SpringBootTest
//@ActiveProfiles("api-test")
class NumberServiceSpockTest extends Specification {

//    @Autowired
//    private NumberService numberService
//
//    def "TEST"() {
//        given:
//        def a = 1
//
//        when:
//        def result = a + 1
//
//        then:
//        log.info(" >>> [TEST] result: {}", result)
//        result == 2
//    }

//    def "TEST2"() {
//        expect:
//        log.info(" >>> input - num: {}, result: {}", num, result)
//        num + 1 == result
//
//        where:
//        num | result
//        1   | 2
//        2   | 3
//    }
//
//    def "특정 로또번호 조회"() {
//        given:
//        def drwtNo = 1070L
//
//        when:
//        def result = numberService.findLottoNumber(drwtNo).block()
//
//        then:
//        log.info(" >>> [특정 로또번호 조회] result: {}", result)
//        result != null
//    }
//
//    def "특정 로또번호 조회 (where blocks)"() {
//        expect:
//        numberService.findLottoNumber(drwtNo).block() != result
//
//        where:
//        drwtNo | result
//        1070L  | null
//    }
}
