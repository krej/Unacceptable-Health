package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class ExercisePlan extends ListableObject {
    @Expose
    Exercise Exercise;
    @Expose
    int Order;
    @Expose
    int Reps;
    @Expose
    double Weight;
    //Time to do a rep. Stored in seconds
    @Expose
    int Seconds;
}
