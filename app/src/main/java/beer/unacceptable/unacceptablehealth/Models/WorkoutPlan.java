package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkoutPlan extends ListableObject implements Serializable {
    @Expose
    public WorkoutType WorkoutType;
    @Expose
    public ArrayList<ExercisePlan> ExercisePlans;
    @Expose
    public ArrayList<CalorieLog> CalorieLog;
    @Expose
    public int TotalCalories;

    public int ExerciseCount() {
        return ExercisePlans.size();
    }
}
