package beer.unacceptable.unacceptablehealth.Models;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.res.ResourcesCompat;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Controllers.IDateLogic;
import beer.unacceptable.unacceptablehealth.R;

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
    @Expose
    public ArrayList<GoalExtension> GoalExtensions;
    @Expose
    public boolean Freestyle;

    public String DurationLabel() {
        return Tools.FormatDate(StartDate, DailyLog.LongDateFormat) + " to " + Tools.FormatDate(EndDate, DailyLog.LongDateFormat);
    }

    public String GoalsCompletedLabel(boolean bCountRestDays) {

        int goalItemCount = goalItemSize(bCountRestDays);

        if (Freestyle) {
            return Integer.toString(goalItemCount);
        } else {
            int goalsCompleted = getGoalsCompleted(bCountRestDays);
            return goalsCompleted + "/" + goalItemCount;
        }
    }

    public String GoalsCompletedPercent(boolean bCountRestDays, boolean bIncludePercentSign) {

        int iPercent = GoalsCompletedPercent(bCountRestDays);
        String sResult = Integer.toString(iPercent);
        if (bIncludePercentSign)
            sResult += "%";

        return sResult;
    }

    private int GoalsCompletedPercent(boolean bCountRestDays) {
        int goalsCompleted = getGoalsCompleted(bCountRestDays);
        int goalItemCount = goalItemSize(bCountRestDays);
        double result = (double)goalsCompleted / (double)goalItemCount;
        result *= 100;

        return (int)result;
    }

    public int GetGoalsCompletedPercentColor(boolean bCountRestDays, Context ctx, IDateLogic dateLogic) {
        int iPercent = GoalsCompletedPercent(bCountRestDays);

        int color;

        Date today = dateLogic.getTodaysDate();

        if (today.after(StartDate) && today.before(EndDate))
            color = ResourcesCompat.getColor(ctx.getResources(), R.color.percentCompleteCurrent, null);
        else if (iPercent >= 90)
            color = ResourcesCompat.getColor(ctx.getResources(), R.color.percentCompletedGood, null);
        else if (iPercent >= 70)
            color = ResourcesCompat.getColor(ctx.getResources(), R.color.percentCompleteOkay, null);
        else
            color = ResourcesCompat.getColor(ctx.getResources(), R.color.percentCompleteBad, null);

        return color;
    }

    private int goalItemSize(boolean bCountRestDays) {
        int i = 0;
        if (GoalItems == null) return i;

        for (GoalItem goalItem : GoalItems) {
            if (!goalItem.WorkoutType.name.equals("Rest") || bCountRestDays)
                i++;
        }

        return i;
    }

    private int getGoalsCompleted(boolean bCountRestDays) {
        int i = 0;
        if (GoalItems == null) return i;

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

    public void addGoalExtension(GoalExtension goalExtension) {
        if (GoalExtensions == null) GoalExtensions = new ArrayList<>();

        GoalExtensions.add(goalExtension);
    }

    public String GetGoalItemsCompletedLabel() {
        //TODO: Move this into the strings.xml file
        if (Freestyle) {
            return "Freestyle Workouts:";
        } else {
            return "Goals Completed:";
        }
    }

    public void AddGoalItem(GoalItem goalItem) {
        if (GoalItems == null) GoalItems = new ArrayList<>();

        GoalItems.add(goalItem);
    }
}
