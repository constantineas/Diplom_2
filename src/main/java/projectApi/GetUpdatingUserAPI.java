package projectApi;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Constants.REQUEST_GET_USER;
import static constants.Constants.REQUEST_PATCH_USER;
import static io.restassured.RestAssured.given;

public class GetUpdatingUserAPI extends BaseApi {
    @Step("Иноформация об авторизованном пользователе")
    public ValidatableResponse userWithAuthorization(String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(REQUEST_GET_USER)
                .then();
    }

    @Step("Изменение данных у авторизованного пользователя")
    public ValidatableResponse changeUserWithAuthorization(String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .patch(REQUEST_PATCH_USER)
                .then();
    }

    @Step("Изменение данных у неавторизованного пользователя")
    public ValidatableResponse changeUserNonAuthorization() {
        return given()
                .spec(requestSpecification())
                .when()
                .patch(REQUEST_PATCH_USER)
                .then();
    }

}
