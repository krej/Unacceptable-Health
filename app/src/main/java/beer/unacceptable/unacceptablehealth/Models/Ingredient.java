package beer.unacceptable.unacceptablehealth.Models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    public String name;
    public double amount;
    public String measure;

    public Ingredient(String name, double amount, String measure) {
        this.name = name;
        this.amount = amount;
        this.measure = measure;
    }
}
