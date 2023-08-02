package cucumber.feature.step.number.edge

import com.john.lotto.common.handler.WebFluxExceptionHandler
import com.john.lotto.number.adapter.`in`.web.NumberRouter
import io.cucumber.java.Before
import io.cucumber.java.ko.그러면
import io.cucumber.java.ko.만약
import io.cucumber.java.ko.먼저
import io.kotest.assertions.print.print
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author yoonho
 * @since 2023.08.03
 */
class NumberEdgeStep {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var router: NumberRouter

    private lateinit var webTestClient: WebTestClient
    private lateinit var result:  WebTestClient.ResponseSpec

    private var drwtNo: Int = 0

    @Before(value = "@number and @docs and @edge")
    fun init() {
        webTestClient = WebTestClient.bindToRouterFunction(router.numberRouterFunction())
            .handlerStrategies(
                HandlerStrategies.builder()
                    .exceptionHandler(WebFluxExceptionHandler())
                    .build()
            )
            .build()
    }

    @먼저("실패 로또번호조회API 호출을 위한 {int} 있다")
    fun 실패_로또번호조회API_호출을_위한_있다(drwtNo: Int) {
        this.drwtNo = drwtNo
    }

    @만약("실패 로또번호조회API {string} 요청하면")
    fun 실패_로또번호조회API_요청하면(url: String) {
        val uriComponents = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("drwtNo", drwtNo)
                .build(false)

        result = webTestClient
                .method(HttpMethod.GET)
                .uri(uriComponents.toUriString())
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
    }

    @그러면("실패 로또번호조회API 호출결과 {int} 확인한다")
    fun 실패_로또번호조회API_호출결과_확인한다(statusCode: Int) {
        result
                .expectStatus().isEqualTo(statusCode)
                .expectBody()
                .print()
    }
}