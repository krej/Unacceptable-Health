package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Calendar;

import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;
import beer.unacceptable.unacceptablehealth.Screens.ViewWorkoutPlan;

public class GoalItemAdapterViewControl extends BaseAdapterViewControl {
    MainScreenController m_oController = new MainScreenController(new Repository(), new DateLogic());
    boolean m_bShowAllGoalItems;
    boolean m_bAllowDelete;
    private WorkoutPlan[] m_oWorkoutPlans;

    public GoalItemAdapterViewControl(boolean bShowAllGoalItems, boolean bAllowDelete, MainScreenController.View view) {
        m_bAlternateRowColors = false;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
        m_bShowAllGoalItems = bShowAllGoalItems;
        m_bAllowDelete = bAllowDelete;
        m_oController.attachView(view);
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView date = view.view.findViewById(R.id.goalitem_date);
        TextView workoutType = view.view.findViewById(R.id.goalitem_workouttype);
        TextView completed = view.view.findViewById(R.id.goalitem_completed);
        FlexboxLayout lstWorkoutPlans = view.view.findViewById(R.id.lstWorkoutPlans);

        GoalItem goalItem = (GoalItem)i;

        date.setText(goalItem.DateFormatted());
        workoutType.setText(goalItem.WorkoutType.name);
        completed.setText(goalItem.CompletedDisplay());
        completed.setTextColor(goalItem.getCompletedTextColor());
        completed.setShadowLayer(1, 1, 1, Color.BLACK);

        ArrayList<WorkoutPlan> workoutPlans = GetWorkoutPlansByWorkoutType(goalItem.WorkoutType);
        if (workoutPlans.size() == 0 || goalItem.Completed) {
            lstWorkoutPlans.setVisibility(View.GONE);
        }

        ClearWorkoutPlanButtons(lstWorkoutPlans);
        CreateWorkoutPlanButtons(workoutPlans, lstWorkoutPlans, view.view.getContext());
    }

    public void setWorkoutPlans(WorkoutPlan[] plans) {
        m_oWorkoutPlans = plans;
    }

    private ArrayList<WorkoutPlan> GetWorkoutPlansByWorkoutType(WorkoutType workoutType) {
        ArrayList<WorkoutPlan> workoutPlans = new ArrayList<>();
        if (m_oWorkoutPlans == null) return workoutPlans;

        for (WorkoutPlan plan : m_oWorkoutPlans) {
            if (plan.WorkoutType.name.equals(workoutType.name))
                workoutPlans.add(plan);
        }

        return workoutPlans;
    }

    private void CreateWorkoutPlanButtons(ArrayList<WorkoutPlan> workoutPlans, FlexboxLayout lstWorkoutPlans, Context c) {
        for (WorkoutPlan plan : workoutPlans) {

            Button b = new Button(c);
            b.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setText(plan.name);
            AddWorkoutPlanButtonClickEvent(b, plan.idString);
            lstWorkoutPlans.addView(b);
        }
    }

    private void ClearWorkoutPlanButtons(FlexboxLayout lstWorkoutPlans) {
        //I'm not entirely sure why this is needed. It fixes duplicated buttons when refreshing. However i'd think that when i delete the list of goal items that would fix it
        //but it doesn't... so I'm doing this
        //i have not tested it with 2 goal items on the same day.
        lstWorkoutPlans.removeAllViews();
    }

    private void AddWorkoutPlanButtonClickEvent(Button b, String idString) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ViewWorkoutPlan.class);
                i.putExtra("idString", idString);
                view.getContext().startActivity(i);

            }
        });
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
                        ShowRefreshToast(v.getContext());
                        break;
                }
            }
        });
        builder.show();
    }

    private void ShowRefreshToast(Context context) {
        Tools.ShowToast(context, "Please Refresh Screen to update all Goal Item Tabs.", Toast.LENGTH_LONG);
    }

    private void ShowCalendar(final Context context, final GoalItem goalItem) {
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

                ShowRefreshToast(context);
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
