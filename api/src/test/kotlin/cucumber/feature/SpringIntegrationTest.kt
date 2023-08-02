package cucumber.feature

import com.john.lotto.ApiApplicationTests
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * @author yoonho
 * @since 2023.08.02
 */
@SpringBootTest(classes = [ApiApplicationTests::class])
@CucumberContextConfiguration
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@ActiveProfiles("api-test")
class SpringIntegrationTest {
}