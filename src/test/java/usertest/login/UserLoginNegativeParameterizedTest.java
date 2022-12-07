package usertest.login;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.Credentials;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class UserLoginNegativeParameterizedTest {
    private UserClient userClient;
    private final User user;
    private final int statusCode;
    private final String message;

    public UserLoginNegativeParameterizedTest(User user, int statusCode, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getTestLoginData() {
        return new Object[][]{
                {UserGenerator.getWithEmailOnly(), SC_UNAUTHORIZED, "email or password are incorrect"},
                {UserGenerator.getWithPasswordOnly(), SC_UNAUTHORIZED, "email or password are incorrect"},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Check response  when User can't logged in  with incorrect credentials")
    public void userCantLoginWithOutOneParameterCheckStatusCode() {
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        int actualSC = responseLogin.extract().statusCode();
        boolean isUserLoggedIn = responseLogin.extract().path("success");
        String actualMessage = responseLogin.extract().path("message");
        assertEquals("Status Code incorrect", statusCode, actualSC);
        assertFalse("Expected false", isUserLoggedIn);
        assertEquals("Message incorrect", message, actualMessage);
    }
}
