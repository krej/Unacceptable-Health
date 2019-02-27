package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class PendingGoalItem extends ListableObject {
    public PendingGoalItem() {
        name = "PendingGoalItem";
    }
    @Expose
    public String Day;
    @Expose
    public WorkoutType Type;
}
