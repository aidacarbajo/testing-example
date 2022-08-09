package frontend.factory.manager;

import frontend.factory.Factory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverManager implements Factory {

    @Override
    public WebDriver createDriver() {
        EdgeOptions options = new EdgeOptions();
        System.out.println(options.toJson());
        //options.setBinary("/Users/carbaaid/drivers4testing/edgedriver.exe");
        return new EdgeDriver();
    }
}
