package cucumber.feature

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

/**
 * @author yoonho
 * @since 2023.08.02
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber/feature")
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "cucumber.feature"
)
@ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty"
)
class RunCucumberTest {
}