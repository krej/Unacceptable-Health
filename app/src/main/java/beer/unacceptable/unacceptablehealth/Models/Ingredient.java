package beer.unacceptable.unacceptablehealth.Models;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class Ingredient extends ListableObject {
    public double amount;
    public String measure;

    public Ingredient(String name, double amount, String measure) {
        this.name = name;
        this.amount = amount;
        this.measure = measure;
    }
}
