package in.athenaeum.springbootintegrationtestsdemo;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Location of feature files
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "in.athenaeum.springbootintegrationtestsdemo.steps") // Step definitions package
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json") // Cucumber reports
public class CucumberIntegrationTestRunner {
}
