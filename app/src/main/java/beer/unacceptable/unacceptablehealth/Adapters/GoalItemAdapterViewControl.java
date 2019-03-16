package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.Calendar;

import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class GoalItemAdapterViewControl extends BaseAdapterViewControl {
    MainScreenController m_oController = new MainScreenController(new Repository(), new DateLogic());
    boolean m_bShowAllGoalItems;
    boolean m_bAllowDelete;

    public GoalItemAdapterViewControl(boolean bShowAllGoalItems, boolean bAllowDelete) {
        m_bAlternateRowColors = true;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
        m_bShowAllGoalItems = bShowAllGoalItems;
        m_bAllowDelete = bAllowDelete;
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView date = view.view.findViewById(R.id.goalitem_date);
        TextView workoutType = view.view.findViewById(R.id.goalitem_workouttype);
        TextView completed = view.view.findViewById(R.id.goalitem_completed);

        GoalItem goalItem = (GoalItem)i;

        date.setText(goalItem.DateFormatted());
        workoutType.setText(goalItem.WorkoutType.name);
        completed.setText(goalItem.CompletedDisplay());

    }

    @Override
    public void onItemClick(View v, ListableObject i) {

    }

    @Override
    public void onItemLongPress(final View v, ListableObject i) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

        final GoalItem goalItem = (GoalItem)i;
        String[] options;
        if (m_bAllowDelete)
            options = new String[3];
        else
            options = new String[2];

        options[0] = goalItem.Completed ? "Uncomplete" : "Complete";
        options[1] = "Move";
        if (m_bAllowDelete)
            options[2] = "Remove";

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Goal Item Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        m_oController.ToggleGoalItemComplete(goalItem, m_Adapter);
                        break;
                    case 1:
                        ShowCalendar(v.getContext(), goalItem);
                        break;
                    case 2:
                        m_oController.DeleteGoalItem(goalItem, m_Adapter);
                        break;
                }
            }
        });
        builder.show();
    }

    private void ShowCalendar(Context context, final GoalItem goalItem) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.HOUR_OF_DAY, 12); //hard code to 12 to ignore timezones
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                m_oController.SetGoalItemDate(cal.getTime(), goalItem, m_Adapter, m_bShowAllGoalItems);
                //m_oController.setDate(cal, dateType);
            }
        };

        DatePickerDialog dialogDatePicker = new DatePickerDialog(context, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialogDatePicker.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        dialogDatePicker.show();
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }
}
