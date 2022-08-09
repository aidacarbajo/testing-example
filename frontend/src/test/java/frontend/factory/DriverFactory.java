package frontend.factory;

import frontend.factory.manager.ChromeDriverManager;
import frontend.factory.manager.EdgeDriverManager;
import frontend.factory.manager.FirefoxDriverManager;
import frontend.factory.manager.SafariDriverManager;
import org.openqa.selenium.WebDriver;

public class DriverFactory {

    public WebDriver createInstance(String browser) {
        WebDriver driver = null;
        BrowserList browserType = BrowserList.valueOf(browser.toUpperCase());

        switch (browserType) {
            case CHROME:
                driver = new ChromeDriverManager().createDriver();
                break;
            case EDGE:
                driver = new EdgeDriverManager().createDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriverManager().createDriver();
                break;
            case SAFARI:
                driver = new SafariDriverManager().createDriver();
                break;
            default:
                break;
        }

        return driver;
    }
}
