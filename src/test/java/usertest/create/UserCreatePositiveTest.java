package usertest.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class UserCreatePositiveTest {
    private UserClient userClient;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.createDefaultUser();
    }

    @After
    public void cleanUp() {
            userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check response and status code when user is created")
    public void userCanBeCreatedAndCheckResponse() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        int actualSC = responseCreate.extract().statusCode();
        boolean isUserCreated = responseCreate.extract().path("success");
        System.out.println(accessToken);
        assertEquals("Status Code incorrect", SC_OK,actualSC);
        assertTrue("Expected true", isUserCreated);
    }
}

