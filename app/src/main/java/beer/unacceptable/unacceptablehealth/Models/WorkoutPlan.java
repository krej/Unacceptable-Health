package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.ArrayList;

public class WorkoutPlan extends ListableObject {
    @Expose
    public WorkoutType WorkoutType;
    @Expose
    public ArrayList<ExercisePlan> ExercisePlans;
    @Expose
    public ArrayList<CalorieLog> CalorieLog;
    @Expose
    public int TotalCalories;
}
