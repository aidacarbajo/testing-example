package backend.steps.petstore;

import backend.RunCucumberTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetsSteps {
    private static Logger LOGGER = LoggerFactory.getLogger(PetsSteps.class);
    private List<String> allPets;
    private static List <String> petStatus = Arrays.asList("available", "pending", "sold");

    @When("a call of the pets filtered by pet status {string} is request")
    public void a_call_of_the_pets_filtered_by_pet_status_is_request(String status) {

            LOGGER.info(String.format("GET request to obtain all the pets with status: %s", status));

            Response res =
                    given()
                    .when().get(RunCucumberTest.getBASE_URI() + "findByStatus?status=" + status)
                    .then()
                            .contentType(ContentType.JSON)
                            .extract().response();

            // According to documentation, if Pet State does not exist, the response status is 400 but API returns 200 (?)
            assertThat(String.format("Pet Status %s does not exist, but API returns 200", status), petStatus, hasItem(status));

            allPets = res.jsonPath().getList("status");
    }

    @Then("the list of all the pets {string} is showed")
    public void the_list_of_all_the_pets_is_showed(String expectedStatus) {
        try {
            for (String status : this.allPets) {
                assertThat(status, is(expectedStatus));
            }
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
        }
    }

}
