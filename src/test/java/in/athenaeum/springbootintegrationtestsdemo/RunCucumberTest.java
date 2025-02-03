package in.athenaeum.springbootintegrationtestsdemo;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "in.athenaeum.springbootintegrationtestsdemo.steps")
//@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary,progress,usage")
@ConfigurationParameter(key = EXECUTION_DRY_RUN_PROPERTY_NAME, value = "true")
public class RunCucumberTest {
}
