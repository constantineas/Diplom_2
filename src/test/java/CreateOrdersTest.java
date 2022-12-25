import ingridients.Ingredients;
import ingridients.IngredientsComp;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.Orders;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import projectApi.CreateOrderAPI;
import projectApi.CreateUserAPI;
import user.Generator;
import user.User;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

@DisplayName("Создание заказа")
public class CreateOrdersTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";
    private Ingredients ingredients;
    private CreateOrderAPI createOrders;
    private List<String> ingredientList;

    @Before
    public void setUp() {
        user = Generator.getDefault();
        createUser = new CreateUserAPI();
        ingredients = new Ingredients();
        createOrders = new CreateOrderAPI();
        ingredients = createOrders.getIngredients();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
        ingredientList = new ArrayList<>();
        for (IngredientsComp comp : ingredients.getData()) {
            ingredientList.add(comp.get_id());
        }
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }


    @Test
    @DisplayName("Создания заказа с авторизацией и без ингредиентов")
    public void сreateOrdersWithAuthorizationAndWithoutIngredientsTest() {
        List<String> notIngredients = List.of("");
        createOrders
                .сreateOrdersWithAuthorization(accessToken, new Orders(notIngredients))
                .assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создания заказа с авторизацией и с ингредиентами")
    public void сreateOrdersWithAuthorizationTest() {
        ValidatableResponse responseCreateOrders =
                createOrders
                        .сreateOrdersWithAuthorization(accessToken, new Orders(ingredientList))
                        .assertThat().statusCode(SC_OK);
        String expected = "true";
        String actual = responseCreateOrders.extract().path("success").toString();
        Assert.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Создания заказа с авторизацией и неверным хешем ингредиентов")
    public void сreateOrdersWithAuthorizationBadHashTest() {
        List<String> badHash = List.of("60d3463f7034a000269f45e9");
        createOrders
                .сreateOrdersWithAuthorization(accessToken, new Orders(badHash))
                .assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Проверка - Создания заказа без авторизации с ингредиентами")
    public void сreateOrdersWithOutAuthorizationTest() {
        ValidatableResponse responseCreateOrders =
                createOrders
                        .сreateOrdersWithoutAuthorization(new Orders(ingredientList))
                        .assertThat().statusCode(SC_OK);
        String expected = "true";
        String actual = responseCreateOrders.extract().path("success").toString();
        Assert.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Проверка - Создания заказа без авторизации без ингредиентов")
    public void сreateOrdersWithoutAuthorizationAndWithoutIngredientsTest() {
        List<String> notIngredients = List.of("60d3463f7034a000269f45edfgdfgdfg45");
        createOrders
                .сreateOrdersWithoutAuthorization(new Orders(notIngredients))
                .assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Проверка - Создания заказа без авторизации с неверным хешем ингредиентов")
    public void сreateOrdersWithoutAuthorizationBadHashTest() {
        List<String> badHash = List.of("60d3463f7034a000269f45e9");
        createOrders
                .сreateOrdersWithoutAuthorization(new Orders(badHash))
                .assertThat().statusCode(SC_BAD_REQUEST);
    }

}
