package frontend.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class CustomWaits {

    WebDriver driver;
    WebDriverWait wait;

    public CustomWaits(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.of(6, ChronoUnit.SECONDS));
        wait.ignoring(StaleElementReferenceException.class);
    }

    public WebElement for_element_present_and_visible(By what) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(what));
    }

    public void for_alert_presence() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void for_element_invisible(WebElement what) {
        wait.until(ExpectedConditions.invisibilityOf(what));
    }

    public void for_element_input_value_changed(WebElement what, String valueExpected) {
        wait.until(ExpectedConditions.textToBePresentInElementValue(what, valueExpected));
    }
}
