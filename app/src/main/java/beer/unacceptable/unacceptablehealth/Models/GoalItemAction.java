package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class GoalItemAction {
    @Expose
    public GoalItem Item;

    @Expose
    public boolean Completed;

    @Expose
    public Date Date;

    @Expose
    public boolean Remove;
}
