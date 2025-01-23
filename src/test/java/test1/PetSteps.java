package test1;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class PetSteps {

    @Step("Add a new pet with details: {pet}")
    public Response addPet(String uri, Pet pet) {
        return RestAssured
                .given()
                .baseUri(uri)
                .contentType("application/json")
                .body(pet)
                .when()
                .post("v2/pet");
    }

    @Step("Retrieve pet by ID: {id}")
    public Response getPet(String uri, int id) {
        return RestAssured
                .given()
                .baseUri(uri)
                .accept("application/json")
                .when()
                .get("v2/pet/" + id);
    }

    @Step("Check that the created pet matches the retrieved pet")
    public void checkPet(Pet petCreated, Pet petGet) {
        Assert.assertEquals(petCreated.getName(), petGet.getName(), "Pet names do not match");
    }

    @Step("Delete pet")
    public Response deletePet(String uri, int id) {
        return RestAssured
                .given()
                .baseUri(uri)
                .when()
                .delete("v2/pet/" + id);
    }
}
