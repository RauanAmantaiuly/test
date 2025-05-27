package restassuredKP2;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserTest {
    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    private static final String TOKEN = "2c7fae79c864a3bfced73d78aac54d214335f871eb27e79b44549e1ac3df9861";
    private UserSteps userSteps = new UserSteps();
    private int id = 78876;

    @Test
    public void test() {
        UserRequest user = UserGenerator.generateUser( "roma", "q@mail.ru", "male", "active");
        UserResponse userResponse = userSteps.addUser(BASE_URL, user);
        Assert.assertEquals(user.getName(), userResponse.getName(), "User name should be equal");

        UserResponse getUser = userSteps.getUserById(BASE_URL, userResponse.getId());
        Assert.assertEquals(getUser.getEmail(), user.getEmail(), "User email should be equal");

        UserRequest duplicateUser = UserGenerator.generateUser( "roma", "q@mail.ru", "male", "active");
        Response response = userSteps.duplicateUser(BASE_URL, duplicateUser);
        List<ErrorResponse> errorResponses = response.jsonPath().getList("", ErrorResponse.class);
        Assert.assertTrue(errorResponses.stream().anyMatch(error -> error.getMessage().equals("has already been taken")));
//        Assert.assertEquals(response.getMessage(), "has already been taken");

        userSteps.deleteUser(BASE_URL, userResponse.getId());

        userSteps.getUserNotFound(BASE_URL, userResponse.getId());
    }
}
