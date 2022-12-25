package projectApi;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.Autorization;
import user.User;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class CreateUserAPI extends BaseApi {
    @Step("Создание нового пользователя")
    public ValidatableResponse сreateUser(User user) {
        return given()
                .spec(requestSpecification())
                .body(user)
                .when()
                .post(REQUEST_POST_CREATE_USER)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(Autorization auth) {
        return given()
                .spec(requestSpecification())
                .body(auth)
                .when()
                .post(REQUEST_POST_LOGIN_USER)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .delete(REQUEST_DELETE_USER)
                .then();
    }

}
