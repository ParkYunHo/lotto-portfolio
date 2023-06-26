package com.john.lotto.cucumber.feature

import io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME
import io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

/**
 * @author yoonho
 * @since 2023.06.23
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber/feature")
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "cucumber.feature"
)
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty"
)
class RunCucumberTest {
}