package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.ArrayList;
import java.util.Date;

public class Goal extends ListableObject {
    @Expose
    public Date StartDate;
    @Expose
    public Date EndDate;
    @Expose
    public String Description;
    @Expose
    public ArrayList<GoalItem> GoalItems;
    @Expose
    public ArrayList<PendingGoalItem> PendingGoalItems;
    @Expose
    public boolean BasedOnWeek;
    //Not sure about this yet
    //@Expose
    //public ArrayList<GoalItemHistory> GoalItemHistory;
    @Expose
    public double OverallGoalAmount;
    @Expose
    public String OverallGoalDescription;
    @Expose
    public boolean Acheived;
}
