package beer.unacceptable.unacceptablehealth.Models;

import java.io.Serializable;

public class Recipe implements Serializable  {

    public String name;
    public Ingredient ingredients[];

    public Recipe(String name, Ingredient ingredients[]) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
