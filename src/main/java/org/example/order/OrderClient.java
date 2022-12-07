package org.example.order;

import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.user.User;


import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static String PATH = "/api/orders/";
    private static String PATH_INGREDIENTS= "/api/ingredients";

    public ValidatableResponse createOrderAuthorizedUser(String accessToken,Order order){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse createOrderUnauthorizedUser(Order order){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse createOrderWithoutIngredients(String accessToken){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .header("Authorization", accessToken)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse getOrdersAuthorizedUser(String accessToken, User vuser){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(PATH)
                .then();
    }

    public ValidatableResponse getOrdersUnauthorizedUser(User user){
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }
}
