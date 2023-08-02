package restdocs.document

import com.john.lotto.common.handler.WebFluxExceptionHandler
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Snippet
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import java.util.function.Consumer

/**
 * @author yoonho
 * @since 2023.08.03
 */
open class RestDocsProcessor {

    lateinit var document: Consumer<EntityExchangeResult<ByteArray>>
    lateinit var restDocumentation: ManualRestDocumentation
    lateinit var webTestClient: WebTestClient
    lateinit var result:  WebTestClient.ResponseSpec

    fun setUp(testClass: Class<Any>, router: RouterFunction<ServerResponse>, vararg snippets: Snippet) {
        this.document = document(
            "{class_name}",
            Preprocessors.preprocessRequest(
                Preprocessors.modifyUris()
                    .scheme("http")
                    .host("138.2.126.76")
                    .port(8080),
                Preprocessors.prettyPrint()
            ),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            *snippets,      // "*": spread operator
        )

        this.restDocumentation = ManualRestDocumentation()
        this.webTestClient = WebTestClient.bindToRouterFunction(router)
            .handlerStrategies(
                HandlerStrategies.builder()
                    .exceptionHandler(WebFluxExceptionHandler())
                    .build()
            )
            .configureClient()
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
        this.restDocumentation.beforeTest(testClass, "")
    }

    open fun tearDown() {
        this.restDocumentation.afterTest()
    }
}