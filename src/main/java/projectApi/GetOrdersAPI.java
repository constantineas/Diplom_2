package projectApi;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Constants.REQUEST_GET_ORDERS;
import static io.restassured.RestAssured.given;

public class GetOrdersAPI extends BaseApi {
    @Step("Cписок заказов авторизованного пользователя")
    public ValidatableResponse getOrdersWithAuthorization(String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(REQUEST_GET_ORDERS)
                .then();
    }

    @Step("Список заказов неавторизованного пользователя")
    public ValidatableResponse getOrdersWithoutAuthorization() {
        return given()
                .spec(requestSpecification())
                .when()
                .get(REQUEST_GET_ORDERS)
                .then();
    }

}
