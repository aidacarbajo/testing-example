package backend;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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


/*
     public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.google.es");
        } catch (Error e) {
            driver.close();
        }
        // Thread.sleep(3000);



    } */
}