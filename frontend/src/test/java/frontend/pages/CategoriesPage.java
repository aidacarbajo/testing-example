package frontend.pages;

import frontend.steps.Ordering;
import frontend.utils.CustomWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoriesPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ordering.class);
    WebDriver driver;
    CustomWaits wait;
    By CartFromMenuLink = By.linkText("Cart");

    public CategoriesPage(WebDriver driver){
        this.driver = driver;
        wait = new CustomWaits(this.driver);
    }

    public void goToCategory(String category) {
        LOGGER.info(String.format("Click on %s", category));
        WebElement aElem = wait.for_element_present_and_visible(By.linkText(category));
        aElem.click();
    }

    public void clickProduct(String product) {
        LOGGER.info(String.format("Click on %s", product));
        WebElement prod = wait.for_element_present_and_visible(By.linkText(product));
        prod.click();
    }

    public void goToCart() {
        LOGGER.info("Go to cart page from menu");
        driver.findElement(CartFromMenuLink).click();
    }
}
