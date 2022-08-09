package frontend.factory.manager;

import frontend.factory.Factory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager implements Factory {
    @Override
    public WebDriver createDriver() {
        return new ChromeDriver();
    }
}
