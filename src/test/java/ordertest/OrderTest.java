package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderClient;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class OrderTest {
    private OrderClient orderClient;
    private String accessToken;
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.createDefaultUser();
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
            userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check response and status code when authorized user is created order")
    public void orderCanBeCreatedWithAuthorizedUser() {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderAuthorizedUser(accessToken, order);
        int actualSC = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected true", isOrderCreated);
    }

    @Test
    @DisplayName("Check response and status code when unauthorized user is created order")
    public void orderCantBeCreatedWithUnauthorizedUser() {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderUnauthorizedUser(order);
        int actualSC = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        assertEquals("Status Code incorrect", actualSC, SC_OK);
        assertTrue("Expected True", isOrderCreated);
    }

    @Test
    @DisplayName("Check response and status code of the order without ingredients")
    public void orderCantBeCreatedWithoutIngredients() {
        ValidatableResponse createOrder = orderClient.createOrderWithoutIngredients(accessToken);
        int actualSC = createOrder.extract().statusCode();
        boolean isOrderCreated = createOrder.extract().path("success");
        String orderMessage = createOrder.extract().path("message");
        assertEquals("Expected 400", SC_BAD_REQUEST, actualSC);
        assertFalse("Expected false", isOrderCreated);
        assertEquals("Ingredient ids must be provided", orderMessage);
    }

    @Test
    @DisplayName("Check the response and status code of the order with incorrect ingredients")
    public void orderCantBeCreatedWithIncorrectOneOfIngredients() {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderAuthorizedUser(accessToken, order);
        int actualSC = createOrder.extract().statusCode();
        assertEquals("Status Code incorrect", SC_INTERNAL_SERVER_ERROR, actualSC);
    }

    @Test
    @DisplayName("Check the response and the status code of the authorized user's order list")
    public void getOrdersAuthorizedUser() {
        Order order = new Order();
        List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(orderList);
        ValidatableResponse createOrder = orderClient.createOrderAuthorizedUser(accessToken, order);
        ValidatableResponse getOrder = orderClient.getOrdersAuthorizedUser(accessToken, user);
        int actualSC = getOrder.extract().statusCode();
        boolean isOrdersDisplayed = getOrder.extract().path("success");
        assertEquals("Status Code incorrect", SC_OK, actualSC);
        assertTrue("Expected True", isOrdersDisplayed);
    }

    @Test
    @DisplayName("Check the response and the status code of the unauthorized user's order list")
    public void getOrdersUnauthorizedUser() {
        ValidatableResponse getOrder = orderClient.getOrdersUnauthorizedUser(user);
        int actualSC = getOrder.extract().statusCode();
        boolean isOrdersDisplayed = getOrder.extract().path("success");
        String actualMessage = getOrder.extract().path("message");
        assertEquals("Status Code incorrect", SC_UNAUTHORIZED, actualSC);
        assertFalse("Expected False", isOrdersDisplayed);
        assertEquals("You should be authorised", actualMessage);
    }
}