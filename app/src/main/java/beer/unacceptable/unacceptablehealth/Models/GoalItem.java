package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.Date;

public class GoalItem extends ListableObject {
    @Expose
    public WorkoutType WorkoutType;
    @Expose
    public Date Date;
    @Expose
    public boolean Completed;

    public GoalItem() {
        super();

        Completed = false;
    }
}
