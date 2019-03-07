package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

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
    public WorkoutType OverallGoalAmountType;
    @Expose
    public boolean Acheived;

    public String DurationLabel() {
        return Tools.FormatDate(StartDate, DailyLog.LongDateFormat) + " to " + Tools.FormatDate(EndDate, DailyLog.LongDateFormat);
    }

    public String GoalsCompletedLabel(boolean bCountRestDays) {
        int goalsCompleted = getGoalsCompleted(bCountRestDays);
        int goalItemCount = goalItemSize(bCountRestDays);
        return goalsCompleted + "/" + goalItemCount;
    }

    private int goalItemSize(boolean bCountRestDays) {
        int i = 0;
        for (GoalItem goalItem : GoalItems) {
            if (!goalItem.WorkoutType.name.equals("Rest") || bCountRestDays)
                i++;
        }

        return i;
    }

    private int getGoalsCompleted(boolean bCountRestDays) {
        int i = 0;
        for (GoalItem goalItem : GoalItems) {
            if (goalItem.Completed && (!goalItem.WorkoutType.name.equals("Rest") || bCountRestDays))
                i++;
        }

        return i;
    }

    public String StartDateFormatted() {
        return Tools.FormatDate(StartDate, DailyLog.LongDateFormat);
    }

    public String EndDateFormatted() {
        return Tools.FormatDate(EndDate, DailyLog.LongDateFormat);
    }
}
