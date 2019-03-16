package beer.unacceptable.unacceptablehealth.Models;

import android.graphics.Color;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

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

    public GoalItem(Date dt, WorkoutType type) {
        this();

        Date = dt;
        WorkoutType = type;
        name = type.name + " on " + Tools.FormatDate(dt, DailyLog.LongDateFormat);
    }

    public String DateFormatted() {
        return Tools.FormatDate(Date, DailyLog.LongDateFormat);
    }

    public String CompletedDisplay() {
        return Completed ? "Complete" : "Incomplete";
    }

    public int getCompletedTextColor() {
        Date dt = new Date();
        if (Completed) {
            return Color.GREEN;
        } else {
            if (Date.after(dt) || Tools.CompareDatesWithoutTime(dt, Date)) {
                return Color.BLUE;
            } else {
                return Color.RED;
            }
        }
    }
}
