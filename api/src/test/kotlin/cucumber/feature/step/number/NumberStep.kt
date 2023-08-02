package cucumber.feature.step.number

import com.john.lotto.number.adapter.`in`.web.NumberRouter
import io.cucumber.java.After
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
import org.springframework.web.util.UriComponentsBuilder
import restdocs.document.RestDocsProcessor
import restdocs.outline.dto.number.NumberFields

/**
 * @author yoonho
 * @since 2023.08.03
 */
class NumberStep: RestDocsProcessor() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var router: NumberRouter

    private var drwtNo: Int = 0

    @Before(value = "@number and @docs")
    fun setUp() {
        super.setUp(
            testClass = this.javaClass,
            router = router.numberRouterFunction(),
            snippets = arrayOf(
                NumberFields.queryParameters(),
                NumberFields.responseFields()
            )
        )
    }

    @After(value = "@number and @docs")
    override fun tearDown() {
        super.tearDown()
    }


    @먼저("로또번호조회API 호출을 위한 {int} 있다")
    fun 로또번호조회API_호출을_위한_있다(drwtNo: Int) {
        this.drwtNo = drwtNo
    }

    @만약("로또번호조회API {string} 요청하면")
    fun 로또번호조회API_요청하면(url: String) {
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

    @그러면("로또번호조회API 호출결과 {int} 확인한다")
    fun 로또번호조회API_호출결과_확인한다(statusCode: Int) {
        result
                .expectStatus().isEqualTo(statusCode)
                .expectBody()
                .consumeWith(
                    this.document
                )
                .print()
    }
}