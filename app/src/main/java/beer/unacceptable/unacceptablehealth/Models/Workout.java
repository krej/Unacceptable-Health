package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.io.Serializable;
import java.util.Date;

public class Workout extends ListableObject implements Serializable {
    @Expose
    public WorkoutPlan WorkoutPlan;
    @Expose
    public String Notes;
    @Expose
    public Date Date;
}
