package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private Response response;

    @Given("the JSONPlaceholder API is up and running")
    public void theAPIIsUpAndRunning() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    @When("I request a post with ID {int}")
    public void iRequestAPostWithID(Integer id) {
        response = given()
                .when()
                .get("/posts/" + id)
                .then()
                .extract()
                .response();
    }

    @Then("I receive a response with status code {int}")
    public void iReceiveAResponseWithStatusCode(Integer statusCode) {
        assertEquals(statusCode, response.statusCode(), "Status code mismatch");
    }

    @And("the title of the post is {string}")
    public void theTitleOfThePostIs(String expectedTitle) {
        String actualTitle = response.jsonPath().getString("title");
        assertEquals(expectedTitle, actualTitle, "Title mismatch");
    }
}
