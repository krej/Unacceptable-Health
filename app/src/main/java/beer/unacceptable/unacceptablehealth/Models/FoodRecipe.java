package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.ArrayList;

public class FoodRecipe extends ListableObject {

    @Expose
    public ArrayList<IngredientAddition> ingredientAdditions;
    /**
     * A list of instructions on how to make the food
     */
    @Expose
    public String instructions; //TODO: Make useful... Also maybe an ordered list instead of a one huge string?
    /**
     * Some possible notes on the recipe. For example, "if you can't find high gluten flour, try to find 12% gluten." or "any brand tomatoes will work but 7/11 are the best"
     */
    @Expose
    public String notes;

    public FoodRecipe(String name, ArrayList<IngredientAddition> ingredientAdditions) {
        this.name = name;
        this.ingredientAdditions = ingredientAdditions;
    }

    public FoodRecipe(String name) {
        this.name = name;
        ingredientAdditions = new ArrayList<>();
    }

    public void ClearIngredients() {
        ingredientAdditions.clear();
    }
}
