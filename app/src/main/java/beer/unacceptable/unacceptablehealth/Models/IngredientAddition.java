package beer.unacceptable.unacceptablehealth.Models;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class IngredientAddition extends ListableObject {
    /**
     * An amount to add. Ex: 4
     */
    public double amount;
    /**
     * A quantifier to the amount. Ex: Cups
     */
    public String measure;
    /**
     * The ingredient to add
     */
    public Ingredient ingredient;

    public IngredientAddition(double amount, String measure, Ingredient ingredient) {
        this.amount = amount;
        this.measure = measure;
        this.ingredient = ingredient;

    }
}
