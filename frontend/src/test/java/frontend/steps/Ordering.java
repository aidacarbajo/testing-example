package frontend.steps;

import frontend.config.ConfigurationManager;
import frontend.factory.DriverFactory;
import frontend.pages.CartPage;
import frontend.pages.CategoriesPage;
import frontend.pages.ProductPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class Ordering {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ordering.class);
    private WebDriver driver;

    ConfigurationManager configuration;
    CartPage cartPage;
    CategoriesPage categoriesPage;
    ProductPage productPage;



    @Given("a new user on the main page of Demoblaze")
    public void a_new_user_on_the_main_page_of_demoblaze() {
        try {
            set_up();
        } catch (Error e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void set_up() {
        LOGGER.info("Open DemoBlaze");
        configuration = new ConfigurationManager();
        driver = new DriverFactory().createInstance(configuration.getBrowser());
        driver.get(configuration.getBaseUrl());
        driver.manage().timeouts().implicitlyWait(Duration.of(3, ChronoUnit.SECONDS));

        categoriesPage = new CategoriesPage(driver);
        cartPage = new CartPage(driver);
        productPage = new ProductPage(driver);
    }

    @When("user navigates to category {string}")
    public void user_navigates_to_category(String category) {
        categoriesPage.goToCategory(category);
    }

    @When("user clicks on product {string}")
    public void user_clicks_on_product(String product) {
        categoriesPage.clickProduct(product);
    }

    @When("user adds product to cart")
    public void user_adds_product_to_cart() {
        productPage.add2Cart();
    }

    @When("user accepts pop up confirmation")
    public void user_accepts_pop_up_confirmation() {
        productPage.accept_pop_up_product_added_to_cart();
    }

    @When("user come back to category {string}")
    public void user_come_back_to_category(String page) {
        driver.navigate().to(configuration.getBaseUrl());
        categoriesPage.goToCategory(page);
    }

    @When("user selects the cart from the menu")
    public void user_selects_the_cart_from_the_menu() {
        categoriesPage.goToCart();
    }


    @When("user deletes {string}")
    public void user_deletes(String product) {
        cartPage.deleteProduct(product);
    }

    @When("user clicks button to buy")
    public void user_clicks_button_to_buy() {
        cartPage.clickPlaceOrderButton();
    }

    @When("user buys the product left with the next info")
    public void user_buys_the_product_left_with_the_next_info(DataTable form) {
        cartPage.setForm(form);
    }

    @Then("the purchase has the correct amount")
    public void thePurchaseHasTheCorrectAmount() {
        cartPage.getIdPurchaseReview();
        assertEquals(cartPage.getTotalAmountFromModalForm(), cartPage.getTotalAmountFromSummary(), "Total price from purchase review does not match with expected");
    }

    @When("user closes purchase review")
    public void user_closes_purchase_review() {
        cartPage.clickOKButton();
    }


    @After()
    public void closeBrowser() {
        driver.quit();
    }
}
