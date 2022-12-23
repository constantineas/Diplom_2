package constants;

public class Constants {

    // URL Stellar Burgers
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    // Создание пользователя
    public static final String  REQUEST_POST_CREATE_USER = "/api/auth/register";

    // Получение данных об ингредиентах
    public static final String  REQUEST_GET_INGREDIENTS = "/api/ingredients";

    // Создание заказа
    public static final String  REQUEST_POST_CREATE_ORDERS = "/api/orders";

    // Авторизация и регистрация
    public static final String  REQUEST_POST_LOGIN_USER = "/api/auth/login";

    // Получение и обновление информации о пользователе
    public static final String  REQUEST_GET_USER = "/api/auth/user";

    // Обновление данных о пользователе
    public static final String  REQUEST_PATCH_USER = "/api/auth/user ";

    // Удаление пользователя
    public static final String  REQUEST_DELETE_USER = "/api/auth/user";


    // Получить заказы конкретного пользователя
    public static final String  REQUEST_GET_ORDERS = "/api/orders";

}
