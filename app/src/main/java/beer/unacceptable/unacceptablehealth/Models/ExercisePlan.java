package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class ExercisePlan extends ListableObject {
    @Expose
    public Exercise Exercise;
    @Expose
    int Order; //TODO: ??? What was this?
    @Expose
    public int Reps;
    @Expose
    public int Sets;
    @Expose
    public double Weight;
    //Time to do a rep. Stored in seconds
    @Expose
    public int Seconds;
}
