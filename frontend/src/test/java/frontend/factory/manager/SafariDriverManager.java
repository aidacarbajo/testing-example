package frontend.factory.manager;

import frontend.factory.Factory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverManager implements Factory {
    @Override
    public WebDriver createDriver() {
        //DriverManager.getInstance(CHROME).setup();
        return new SafariDriver();
    }
}
