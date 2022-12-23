import ProjectApi.CreateUserAPI;
import ProjectApi.GetUpdatingUserAPI;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.Generator;
import user.User;

import static org.apache.http.HttpStatus.*;

@DisplayName("Изменение данных пользователя")
public class GetUpdatingUserTest {

    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";
    private GetUpdatingUserAPI userDataWithAuthorization;

    @Before
    public void setUp() {
        user = Generator.getDefault();
        createUser = new CreateUserAPI();
        userDataWithAuthorization = new GetUpdatingUserAPI();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Получение информации о данных пользователя с авторизацией")
    public void user() {
        ValidatableResponse infoUserData =
                userDataWithAuthorization.userWithAuthorization(accessToken)
                        .assertThat().statusCode(SC_OK);

        String expectedSuccess = "true";
        String actualSuccess = infoUserData.extract().path("success").toString();
        String expectedUser = "{email=kostya-1400@yandex.ru, name=Konstantin}";
        String actualUser = infoUserData.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Изменения данных пользователя с авторизацией")
    public void userСhangeWithAuthorization() {
        ValidatableResponse infoUserData =
                userDataWithAuthorization.changeUserWithAuthorization(accessToken)
                        .assertThat().statusCode(SC_OK);
        String expectedSuccess = "true";
        String actualSuccess = infoUserData.extract().path("success").toString();
        String expectedUser = "{email=kostya-1400@yandex.ru, name=Konstantin}";
        String actualUser = infoUserData.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void userСhangeWithoutAuthorization() {
        ValidatableResponse infoUserData =
                userDataWithAuthorization.changeUserNonAuthorization()
                        .assertThat().statusCode(SC_UNAUTHORIZED);
        String expectedSuccess = "false";
        String actualSuccess = infoUserData.extract().path("success").toString();
        String expectedMessage = "You should be authorised";
        String actualMessage = infoUserData.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
