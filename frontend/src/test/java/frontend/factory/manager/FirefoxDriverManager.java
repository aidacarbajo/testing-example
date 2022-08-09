package frontend.factory.manager;

import frontend.factory.Factory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager implements Factory {
    @Override
    public WebDriver createDriver() {
        return new FirefoxDriver();
    }
}
