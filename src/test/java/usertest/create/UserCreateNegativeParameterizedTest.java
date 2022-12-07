package usertest.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class UserCreateNegativeParameterizedTest {
    private  UserClient userClient;
    private final User user;
    private final int statusCode;
    private final String message;

    public UserCreateNegativeParameterizedTest(User user, int statusCode, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.message = message;
    }

    //test data
@Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {UserGenerator.getWithEmailOnly(),SC_FORBIDDEN,"Email, password and name are required fields"},
                {UserGenerator.getWithPasswordOnly(),SC_FORBIDDEN,"Email, password and name are required fields"}
        };
}

    @Before
    public void setUp() {
        userClient = new UserClient();

    }

    @Test
    @DisplayName("Check response  when User can't create with one null field ")
    public void createUserWithOutOneParameterCheckStatusCode(){
        ValidatableResponse responseCreate = userClient.create(user);
        int actualSC = responseCreate.extract().statusCode();
        boolean isUserCreated = responseCreate.extract().path("success");
        String actualMessage = responseCreate.extract().path("message" );
        assertEquals("Status Code incorrect",statusCode, actualSC);
        assertFalse("Expected false",isUserCreated);
        assertEquals("Message incorrect",message,actualMessage);
    }
}
