package beer.unacceptable.unacceptablehealth.Models;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class Recipe extends ListableObject {

    public Ingredient ingredients[];

    public Recipe(String name, Ingredient ingredients[]) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
