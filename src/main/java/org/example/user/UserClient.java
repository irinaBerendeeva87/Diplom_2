package org.example.user;

import io.restassured.response.ValidatableResponse;
import org.example.Client;
import static io.restassured.RestAssured.given;

public class UserClient  extends Client {

    private static final String PATH="api/auth/register/";
    private static final String PATH_LOGIN = "/api/auth/login/";
    private static final String PATH_DELETE = "/api/auth/user/";

    public ValidatableResponse create(User user){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .body(user)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse login(Credentials credentials){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(PATH_DELETE)
                .then();
    }
}
