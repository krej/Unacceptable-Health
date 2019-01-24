package beer.unacceptable.unacceptablehealth.Models;

import java.io.Serializable;

import beer.unacceptable.unacceptablehealth.Tools.ListableObject;

public class Ingredient extends ListableObject {
    public double amount;
    public String measure;

    public Ingredient(String name, double amount, String measure) {
        this.name = name;
        this.amount = amount;
        this.measure = measure;
    }
}
