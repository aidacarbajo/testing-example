package backend;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("backend")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")

public class RunCucumberTest {

    private static final String BASE_URI = "https://petstore.swagger.io/v2/pet/";

    public static String getBASE_URI() {
        return BASE_URI;
    }

}