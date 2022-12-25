import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import projectapi.CreateUserAPI;
import user.Autorization;
import user.Generator;
import user.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@DisplayName("Логин пользователя")
public class LoginUserTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";

    @Before
    public void setUp() {
        user = Generator.getDefault();
        createUser = new CreateUserAPI();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Авторизации под существующим пользователем")
    public void userCanBeLogin() {
        ValidatableResponse responseLoginUser =
                createUser.loginUser(Autorization.from(user))
                        .assertThat().statusCode(SC_OK);
        String expectedSuccess = "true";
        String actualSuccess = responseLoginUser.extract().path("success").toString();
        String expectedUser = "{email=kostya-1400@yandex.ru, name=Konstantin}";
        String actualUser = responseLoginUser.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Авторизации с неверным логином")
    public void userCanBeCreatedWithNeverLogin() {
        user.setEmail("kostya-14324534@yandex.ru");
        user.setPassword("bestPassword444");
        createUser.loginUser(Autorization.from(user))
                .assertThat().statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Авторизации с неверным паролем")
    public void userCanBeCreatedWithOutLoginAndPassword() {
        user.setEmail("kostya-1400@yandex.ru");
        user.setPassword("dhf6un6u5r6n");
        createUser.loginUser(Autorization.from(user))
                .assertThat().statusCode(SC_UNAUTHORIZED);
    }

}
