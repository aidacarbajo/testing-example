package frontend.pages;

import frontend.steps.Ordering;
import frontend.utils.CustomWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ordering.class);
    WebDriver driver;
    CustomWaits wait;
    By add2CartButton = By.linkText("Add to cart");

    public ProductPage(WebDriver driver){
        this.driver = driver;
        wait = new CustomWaits(this.driver);
    }

    public void add2Cart() {
        LOGGER.info("Adding product to cart");
        WebElement add =  wait.for_element_present_and_visible(add2CartButton);
        add.click();
    }

    public void accept_pop_up_product_added_to_cart() {
        wait.for_alert_presence();
        driver.switchTo().alert().accept();
    }
}

