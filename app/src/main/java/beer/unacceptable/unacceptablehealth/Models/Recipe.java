package beer.unacceptable.unacceptablehealth.Models;

import java.io.Serializable;

import beer.unacceptable.unacceptablehealth.Tools.ListableObject;

public class Recipe extends ListableObject {

    public Ingredient ingredients[];

    public Recipe(String name, Ingredient ingredients[]) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
