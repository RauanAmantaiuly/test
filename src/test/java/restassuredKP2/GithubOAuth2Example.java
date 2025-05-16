package restassuredKP2;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GithubOAuth2Example {
    private static final String GITHUB_USER_API = "https://api.github.com/user";
    private static final String ACCESS_TOKEN    = "";

    private static final String ID_TOKEN = "";
    private static final String AUTH0_API_URL = "https://dev-cqpfdd0fpbkszfbb.us.auth0.com";

    @Test
    public void gitHubOAuthTest() {
        Response response = RestAssured
                .given()
                .auth().oauth2(ACCESS_TOKEN)
                .when()
                .get(GITHUB_USER_API)
                .then()
                .statusCode(200)
                .log().body()
                .extract().response();

        String login = response.jsonPath().getString("login");
        Assert.assertNotNull(login, "github login shouldn't be not null");
    }

    @Test
    public void openIdTest() {
        RestAssured
                .given()
                .header("Authorization", "Bearer " + ID_TOKEN)
                .when()
                .get(AUTH0_API_URL)
                .then()
                .statusCode(200)
                .log().body();
    }
}
