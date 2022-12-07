package usertest.change;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.Credentials;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class ChangeAuthorizedUserDataTest {

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
    @DisplayName("Check response and status code when authorized user change name")
    public void authorizedUserCanChangeName(){
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        String  userOldName = user.getName();
        user.setName("bla-bal");
        boolean isUserChangeData = responseLogin.extract().path("success");
        String newName = user.getName();
        int actualSC = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected true", isUserChangeData);
        assertNotEquals("Expected different name",userOldName, newName);

    }

    @Test
    @DisplayName("Check response and status code when authorized user change name")
    public void authorizedUserCanChangeEmail(){
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        String OldEmail =  user.getEmail();
        user.setEmail("bla-bla@gmail.com");
        boolean isUserChangeData = responseLogin.extract().path("success");
        String newEmail = user.getEmail();
        int actualSC = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected true", isUserChangeData);
        assertNotEquals("Expected different Email", OldEmail, newEmail);
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check response and status code when authorized user change name")
    public void authorizedUserCantChangeEmailOnExistsEmail(){
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        String userEmail = user.getEmail();
        User changedUser = new User("bla-bla@gmail.com", "bla-bla", "alex");
        ValidatableResponse responseCreate1 = userClient.create(changedUser);
        ValidatableResponse responseUpdateExistsEmail = userClient.updateAuthorizedUserData(accessToken,Credentials.from(changedUser));
        String changedEmail = changedUser.getEmail();
        int actualStatusCodeChange = responseUpdateExistsEmail.extract().statusCode();
        boolean isUserChangeData = responseUpdateExistsEmail.extract().path("success");
        String changeDataMessage =  responseUpdateExistsEmail.extract().path("message");
        assertEquals("Expected 403",SC_FORBIDDEN, actualStatusCodeChange);
        assertNotEquals(userEmail, changedEmail);
        assertFalse("Expected false",isUserChangeData);
        assertEquals("User with such email already exists",changeDataMessage);
    }

    @Test
    @DisplayName("Check response and status code when authorized user change password")
    public void authorizedUserCanChangePassword(){
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseCreate.extract().path("accessToken");
        String oldPassword = user.getPassword();
        user.setPassword("bla-bla_car");
        boolean isUserChangeData = responseLogin.extract().path("success");
        String newPassword = user.getPassword();
        int actualSC = responseLogin.extract().statusCode();
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected true", isUserChangeData);
        assertNotEquals("Expected different Email",oldPassword, newPassword);
    }
}
