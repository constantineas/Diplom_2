package ingridients;

import java.util.List;

public class Ingredients {

    boolean success;
    List<IngredientsComp> data;

    public Ingredients(){};

    public Ingredients(boolean success, List<IngredientsComp> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IngredientsComp> getData() {
        return data;
    }

    public void setData(List<IngredientsComp> data) {
        this.data = data;
    }

}
