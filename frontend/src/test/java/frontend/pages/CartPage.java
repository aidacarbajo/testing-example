package frontend.pages;

import frontend.steps.Ordering;
import frontend.utils.CustomWaits;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CartPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ordering.class);
    WebDriver driver;
    CustomWaits wait;

    By modal = By.xpath("//*[@id=\"orderModal\"]");

    By placeOrderButton = By.xpath("//button[text()=\"Place Order\"]");
    By purchaseButton = By.xpath("//button[text()=\"Purchase\"]");
    By OKButton = By.xpath("//button[text()=\"OK\"]");


    By deleteLink = By.linkText("Delete");

    By totalAmountText = By.id("totalm");

    By purchaseReviewInfo = By.className("lead");


    public CartPage(WebDriver driver){
        this.driver = driver;
        wait = new CustomWaits(this.driver);
    }

    public WebElement getModal() {
        return driver.findElement(modal);
    }

    private WebElement getPurchaseButton() {
        return getModal().findElement(purchaseButton);
    }

    private WebElement getPlaceOrderButton() {
        return driver.findElement(placeOrderButton);
    }

    private WebElement getOKButton() {
        return getModal().findElement(OKButton);
    }

    public String[] getPurchaseReviewInfo() {
        return driver.findElement(purchaseReviewInfo).getText().split("\n");
    }

    public void getIdPurchaseReview() {
        String id = getPurchaseReviewInfo()[0].split(":")[1].trim();
        LOGGER.info(String.format("ID: %s", id));
    }

    public String getTotalAmountFromModalForm() {
        return driver.findElement(totalAmountText).getText().split("Total:")[1].trim();
    }

    public String getTotalAmountFromSummary() {
        String amount_coinType = getPurchaseReviewInfo()[1].split(":")[1].trim();
        LOGGER.info(String.format("Total amount: %s", amount_coinType));
        return amount_coinType.split(" ")[0];
    }

    private WebElement getInput(String inputID) {
        return driver.findElement(By.id(inputID));
    }

    private void sendKeys(String inputID, String inputValue) {
        LOGGER.info(String.format("Filling %s with %s", inputID, inputValue));
        getInput(inputID).sendKeys(inputValue);
        wait.for_element_input_value_changed(getInput(inputID), inputValue);
    }

    public void setForm(DataTable form) {
        List<Map<String, String>> formKeyValue = form.asMaps(String.class, String.class);

        for (Map<String, String> component : formKeyValue) {
            for (String inputID : component.keySet()) {
                sendKeys(inputID, component.get(inputID));
            }
        }

        clickPurchaseButton();
    }

    public void clickPlaceOrderButton() {
        LOGGER.info("Click on Place Order Button");
        getPlaceOrderButton().click();
    }

    public void clickPurchaseButton() {
        LOGGER.info("Click on Purchase Button");
        getPurchaseButton().click();
    }

    public void clickOKButton() {
        LOGGER.info("Click OK Button");
        getOKButton().click();
    }

    public void deleteProduct(String product) {
        WebElement table = wait.for_element_present_and_visible(By.tagName("table"));
        WebElement tableBody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

        int numRow = -1;

        LOGGER.info(String.format("Looking for product %s in the cart", product));

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));

            if (columns.size() > 0 && columns.get(1).getText().equals(product)) {
                numRow = i;
                WebElement deleteLinkElement = columns.get(3).findElement(deleteLink);
                deleteLinkElement.click();
                wait.for_element_invisible(deleteLinkElement);

                LOGGER.info(String.format("Product in row: %s", numRow));
                LOGGER.info(String.format("%s deleted successfully", product));

                break;
            }
        }

        if (numRow == -1) {
            LOGGER.error("Product not found");
        }

    }


}