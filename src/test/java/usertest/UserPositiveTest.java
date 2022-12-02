package usertest;

import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserPositiveTest {
    private UserClient userClient;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.defaultUser;
    }

    @After
    public void cleanUp(){
        userClient.delete(accessToken);
    }

    @Test
    public void userCanBeCreated (){
        ValidatableResponse responseCreate = userClient.create(user);
        int actualSC = responseCreate.extract().statusCode();
        boolean isUserCreated = responseCreate.extract().path("success");
        accessToken = responseCreate.extract().path("accessToken");
        assertEquals("Status Code incorrect",actualSC, SC_OK);
        assertTrue("Expected true",isUserCreated);
    }
}
