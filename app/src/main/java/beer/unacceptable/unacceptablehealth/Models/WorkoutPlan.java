package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class WorkoutPlan extends ListableObject implements Serializable {
    @Expose
    public WorkoutType WorkoutType;
    @Expose
    public ArrayList<ExercisePlan> ExercisePlans;
    @Expose
    public ArrayList<CalorieLog> CalorieLog;
    @Expose
    public int TotalCalories;
    @Expose
    public Date LastUsed;

    public int ExerciseCount() {
        return ExercisePlans.size();
    }

    public boolean HasChanges;

    public int GetLastUsedAsInt() {
        //20200911
        //0911
        int year = LastUsed.getYear() * 1000;
        int month = LastUsed.getMonth() * 100;
        int day = LastUsed.getDay();

        return -1 * (year + month + day);
    }
}
