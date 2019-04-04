package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.Date;

public class Workout extends ListableObject {
    @Expose
    WorkoutPlan WorkoutPlan;
    @Expose
    String Notes;
    @Expose
    Date Date;
}
