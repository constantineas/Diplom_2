import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import projectapi.CreateUserAPI;
import user.Generator;
import user.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

@DisplayName("Создание пользователя")
public class CreateUserTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";

    @Before
    public void setUp() {
        user = Generator.getDefault();
        createUser = new CreateUserAPI();
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Cоздание уникального пользователя")
    public void userCreated() {
        ValidatableResponse responseCreateUser =
                createUser.сreateUser(user)
                        .assertThat().statusCode(SC_OK);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
        String expectedSuccess = "true";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedUser = "{email=kostya-1400@yandex.ru, name=Konstantin}";
        String actualUser = responseCreateUser.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void userRegisterCreated() {
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
        responseCreateUser =
                createUser.сreateUser(user)
                        .assertThat().statusCode(SC_FORBIDDEN);
        String expectedSuccess = "false";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedMessage = "User already exists";
        String actualMessage = responseCreateUser.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей")
    public void userCreatedWithoutFields() {
        user.setPassword("");
        ValidatableResponse responseCreateUser =
                createUser.сreateUser(user)
                        .assertThat().statusCode(SC_FORBIDDEN);
        String expectedSuccess = "false";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = responseCreateUser.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
