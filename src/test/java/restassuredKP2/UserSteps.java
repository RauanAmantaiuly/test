package restassuredKP2;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserSteps {

    @Step("add User")
    public UserResponse addUser(String uri, UserRequest user) {
        return RestAssured
                .given()
                .baseUri(uri)
                .header("Authorization", "Bearer 2c7fae79c864a3bfced73d78aac54d214335f871eb27e79b44549e1ac3df9861")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .as(UserResponse.class);
    }

    @Step("get User")
    public UserResponse getUserById(String uri, int id) {
        return RestAssured
                .given()
                .baseUri(uri)
                .header("Authorization", "Bearer 2c7fae79c864a3bfced73d78aac54d214335f871eb27e79b44549e1ac3df9861")
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

    }

    @Step("duplicate User")
    public Response duplicateUser(String uri, UserRequest userRequest) {
        return RestAssured
                .given()
                .baseUri(uri)
                .header("Authorization", "Bearer 2c7fae79c864a3bfced73d78aac54d214335f871eb27e79b44549e1ac3df9861")
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(422)
                .extract().response();
    }

    @Step("delete User")
    public Response deleteUser(String uri, int id) {
        return RestAssured
                .given()
                .baseUri(uri)
                .header("Authorization", "Bearer 2c7fae79c864a3bfced73d78aac54d214335f871eb27e79b44549e1ac3df9861")
                .when()
                .delete("/users/" + id)
                .then()
                .statusCode(204)
                .extract().response();
    }

    @Step("get User Not Found")
    public Response getUserNotFound(String uri, int id) {
        return RestAssured.given()
                .baseUri(uri)
                .header("Authorization", "Bearer 2c7fae79c864a3bfced73d78aac54d214335f871eb27e79b44549e1ac3df9861")
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(404)
                .extract().response();
    }
}
