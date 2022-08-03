package frontend.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class Ordering {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ordering.class);
    private final WebDriver driver = new ChromeDriver();

    private String totalAmount;

    @Given("a new user on the main page of Demoblaze")
    public void a_new_user_on_the_main_page_of_demoblaze() {
        try {
            LOGGER.info("Maximize window and open DemoBlaze");
            driver.manage().window().maximize();
            driver.get("https://www.demoblaze.com");
            Thread.sleep(500);
        } catch (Error e) {
            LOGGER.error(e.getMessage());
            driver.close();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to {string}")
    public void user_navigates_to(String category) throws InterruptedException {
        linkClick(category);
    }

    @When("user clicks on {string}")
    public void user_clicks_on(String button) throws InterruptedException {
        linkClick(button);
    }

    @When("user accepts pop up confirmation")
    public void user_accepts_pop_up_confirmation() throws InterruptedException {
        Thread.sleep(1000);
        driver.switchTo().alert().accept();
    }

    @When("user come back to {string}")
    public void user_come_back_to(String page) throws InterruptedException {
        driver.navigate().to("https://www.demoblaze.com/index.html#");
        linkClick(page);
    }

    @When("user deletes {string}")
    public void user_deletes(String product) {
        WebElement table = driver.findElement(By.tagName("table"));
        WebElement tableBody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        int numRow = -1;

        LOGGER.info(String.format("Looking for product %s in the cart", product));

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
            if (columns.size() > 0 && columns.get(1).getText().equals(product)) {
                numRow = i;
                LOGGER.info(String.format("Product in line: %s", numRow));
                columns.get(3).findElement(By.linkText("Delete")).click();
                LOGGER.info(String.format("%s deleted successfully", product));
                break;
            }
        }

        if (numRow == -1) {
            LOGGER.error("Product not found");
        }
    }

    @When("user fills every input form")
    public void user_fills_every_input_form(DataTable form) throws InterruptedException {
        LOGGER.info("Filling form");
        LOGGER.info(String.valueOf(form));

        List<Map<String, String>> user = form.asMaps(String.class, String.class);
        totalAmount = driver.findElement(By.id("totalm")).getText().split("Total: ")[1];

        for (Map<String, String> component : user) {
            for (String a : component.keySet()) {
                sendKeys(a, component.get(a));
            }
        }

        Thread.sleep(1000);
    }

    @When("user clicks on button {string}")
    public void user_clicks_on_button(String buttonText) throws InterruptedException {
        Thread.sleep(1000);
        WebElement modal = driver.findElement(By.xpath("//*[@id=\"orderModal\"]"));
        modal.findElement(By.xpath(String.format("//button[text()=\'%s\']", buttonText))).click();
        Thread.sleep(1000);
    }

    @Then("the purchase has the correct amount")
    public void thePurchaseHasTheCorrectAmount() throws InterruptedException, IOException {
        WebElement p = driver.findElement(By.className("lead"));
        p.getScreenshotAs(OutputType.FILE);

        String[] allData = p.getText().split("\n");
        String id = allData[0].split(": ")[1];
        String amountAndType = allData[1].split(": ")[1];
        String amount = amountAndType.split(" ")[0];

        LOGGER.info(String.format("Product ID: %s", id));
        LOGGER.info(String.format("Product amount: %s", amount));

        Assertions.assertThat(amount).isEqualTo(totalAmount);
    }

    public void linkClick(String linkTxt) throws InterruptedException {
        LOGGER.info(String.format("Click on %s", linkTxt));
        WebElement aElem = new WebDriverWait(driver, Duration.of(5, ChronoUnit.SECONDS)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkTxt)));
        aElem.click();
        Thread.sleep(1500);
    }

    public void sendKeys(String inputId, String inputValue) throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(By.id(inputId)).sendKeys(inputValue);
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }
}
