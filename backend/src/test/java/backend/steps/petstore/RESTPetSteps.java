package backend.steps.petstore;

import backend.RunCucumberTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

public class RESTPetSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(RESTPetSteps.class);
    private static Response resPost;

    private int petID;
    private String petName;
    private String petStatus;
    private String petCategory;
    private int deleteId;

    @When("the user adds a new {string} with {string} name, {string} status and {int} as a id")
    public void the_user_adds_a_new_with_name_status_and_as_a_id(String category, String name, String status, int id) {
        try {
            resPost = given().contentType(ContentType.JSON)
                    .body(makePet(name, category, status, id))
                    .when().post(RunCucumberTest.getBASE_URI())
                    .then().extract().response();
            petName = resPost.then().extract().path("name");

        } catch (Error e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Then("the pet name is {string}")
    public void the_pet_name_is(String name) {
        int expectedStatus = name.isEmpty() ? 405 : 200;

        if(expectedStatus == 200) {
            LOGGER.info("Pet posted successfully");
            assertThat(name).isEqualTo(this.petName);
        } else {
            LOGGER.error("Name and photoUrl are required inputs, but API returns status 200 instead of 405");
            assertThat(resPost.getStatusCode()).isEqualTo(expectedStatus);
        }

    }

    @When("a user buys the new pet created")
    public void a_user_buys_the_new_pet_created() {
        LOGGER.info(String.format("User buys previous pet with id %s", petID));

        given().contentType(ContentType.JSON)
                .body(makePet(petName, petCategory, "sold", petID))
                .when().put(RunCucumberTest.getBASE_URI());
    }

    @Then("the pet status is {string}")
    public void the_pet_status_is(String status) {
        LOGGER.info(String.format("The status of the pet is %s", this.petStatus));
        assertThat(status).isEqualTo(this.petStatus);
    }


    @When("a delete request with the same pet is getting done")
    public void a_delete_request_with_the_same_pet_is_getting_done() {
        LOGGER.info(String.format("Deletion request of the pet %s is requested", this.petID));

        deleteId = parseInt(given().contentType(ContentType.JSON)
                .header("api_key", "my_api_key")
                .pathParam("petId", petID)
                .when().delete(RunCucumberTest.getBASE_URI() + "{petId}")
                .then().statusCode(200)
                .extract().path("message"));
    }

    @Then("the {int} is the same as the deleted one")
    public void the_is_the_same_as_the_deleted_one(Integer petId) {
        LOGGER.info("Pet deleted successfully");
        assertThat(deleteId).isEqualTo(petId);
    }

    private String makePet(String name, String category, String status, int id) {
        if (id != petID) {
            LOGGER.info(String.format("Creating new %s with name %s", category, name));
        }

        petStatus = status;
        petCategory = category;
        petID = id;
        String categoryId = String.valueOf(Math.abs(new Random().nextInt()));

        return String.format(
                "{\n" +
                        "  \"id\": %s,\n" +
                        "  \"category\": {\n" +
                        "       \"id\": %s,\n" +
                        "       \"name\": \"%s\"\n" +
                        "  },\n" +
                        (name != null && !name.isEmpty() ? ("  \"name\": \""+ name +"\",\n"): "") +
                        "  \"photoUrls\": [],\n" +
                        "  \"tags\": [],\n" +
                        "  \"status\": \"%s\"\n" +
                        "}",
                petID,
                categoryId,
                petCategory,
                petStatus
        );
    }


}
