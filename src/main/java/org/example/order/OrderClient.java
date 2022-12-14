package org.example.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.user.User;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH = "/api/orders/";

    @Step("Create order with Authorized User")
    public ValidatableResponse createOrderAuthorizedUser(String accessToken,Order order){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Create order with Unauthorized User")
    public ValidatableResponse createOrderUnauthorizedUser(Order order){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Create order without ingredients")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken){
        return given()
                .spec(getSpec())//настраивает запрос который будет исполнен
                .header("Authorization", accessToken)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Get order from Authorized User")
    public ValidatableResponse getOrdersAuthorizedUser(String accessToken, User vuser){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(PATH)
                .then();
    }

    @Step("Get order from Unauthorized User")
    public ValidatableResponse getOrdersUnauthorizedUser(User user){
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }
}
