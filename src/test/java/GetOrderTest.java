import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import projectapi.CreateUserAPI;
import projectapi.GetOrdersAPI;
import user.Generator;
import user.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@DisplayName("Получение заказов конкретного пользователя")
public class GetOrderTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";
    private GetOrdersAPI getOrdersList;

    @Before
    public void setUp() {
        user = Generator.getDefault();
        createUser = new CreateUserAPI();
        getOrdersList = new GetOrdersAPI();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Получения списка заказов пользователя с авторизацией")
    public void getUserListOrdersWithAuthorizationTest() {
        ValidatableResponse responseCreateUser =
                getOrdersList.getOrdersWithAuthorization(accessToken)
                        .assertThat().statusCode(SC_OK);
        String expectedSuccess = "true";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
    }

    @Test
    @DisplayName("Получения списка заказов пользователя без авторизации")
    public void getUserListOrdersWithoutAuthorizationTest() {
        ValidatableResponse responseCreateUser =
                getOrdersList.getOrdersWithoutAuthorization()
                        .assertThat().statusCode(SC_UNAUTHORIZED);
        String expectedSuccess = "false";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedMessage = "You should be authorised";
        String actualMessage = responseCreateUser.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
