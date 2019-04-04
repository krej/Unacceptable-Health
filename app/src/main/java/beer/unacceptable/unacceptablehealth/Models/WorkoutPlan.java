package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.ArrayList;

public class WorkoutPlan extends ListableObject {
    @Expose
    WorkoutType WorkoutType;
    @Expose
    ArrayList<ExercisePlan> ExercisePlans;
    @Expose
    ArrayList<CalorieLog> CalorieLog;
    @Expose
    int TotalCalories;
}
