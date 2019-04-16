package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.ArrayList;

public class Exercise extends ListableObject {
    @Expose
    public ArrayList<Muscle> Muscles;
    @Expose
    public boolean ShowWeight;
    @Expose
    public boolean ShowTime;
    @Expose
    public boolean ShowReps;
    @Expose
    public String Description;
}
