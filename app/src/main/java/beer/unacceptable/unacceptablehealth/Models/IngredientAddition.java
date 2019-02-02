package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class IngredientAddition extends ListableObject {
    /**
     * An amount to add. Ex: 4
     */
    @Expose
    public double amount;
    /**
     * A quantifier to the amount. Ex: Cups
     */
    @Expose
    public String measure;
    /**
     * The ingredient to add
     */
    @Expose
    public Ingredient ingredient;

    public IngredientAddition(double amount, String measure, Ingredient ingredient) {
        this.amount = amount;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    public String toString() {
        if (ingredient == null) return "Empty";

        return ingredient.name + " " + amount + " " + measure;
    }
}
