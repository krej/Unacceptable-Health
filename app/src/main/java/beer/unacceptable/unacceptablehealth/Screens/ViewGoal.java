package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Adapters.GoalItemAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.ViewGoalController;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewGoal
        extends BaseActivity
        implements ViewGoalController.View {

    //A lot of this is copy and pasted from CreateGoal. Maybe I should combine these? I don't know...
    Switch m_swGoalType;
    TextView m_etStartDate;
    TextView m_etEndDate;
    TextView m_tvName;
    TextView m_etBriefDescription;
    Spinner m_spGoalAmountType;
    EditText m_etGoalAmount;
    RecyclerView m_rvGoalItems;
    NewAdapter m_Adapter;

    ViewGoalController m_oController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_oController = new ViewGoalController(new Repository());
        m_oController.attachView(this);
        m_oController.loadWorkoutTypes();

        FindUIElements();
        LockDownScreen();

        m_swGoalType.setVisibility(View.GONE);

        m_Adapter = Tools.setupRecyclerView(m_rvGoalItems, getApplicationContext(), R.layout.list_goal_item, 0, false, new GoalItemAdapterViewControl(true, true));

        Intent intent = getIntent();
        String sIdString = intent.getStringExtra("id");
        m_oController.loadGoal(sIdString);
    }

    private void LockDownScreen() {
        m_etGoalAmount.setEnabled(false);
        m_etBriefDescription.setEnabled(false);
        m_tvName.setEnabled(false);
        m_spGoalAmountType.setEnabled(false);
    }

    private void FindUIElements() {
        m_etStartDate = findViewById(R.id.goal_start_date);
        m_etEndDate = findViewById(R.id.goal_end_date);
        m_rvGoalItems = findViewById(R.id.goal_items);
        m_etBriefDescription = findViewById(R.id.goal_description);
        m_swGoalType = findViewById(R.id.goal_create_based_on_week);
        m_tvName = findViewById(R.id.goal_name);
        m_spGoalAmountType = findViewById(R.id.goal_amount_type);
        m_etGoalAmount = findViewById(R.id.goal_amount);
    }

    @Override
    public void PopulateScreen(Goal goal) {
        m_tvName.setText(goal.name);
        m_etStartDate.setText(goal.StartDateFormatted());
        m_etEndDate.setText(goal.EndDateFormatted());
        m_etBriefDescription.setText(goal.Description);
        m_etGoalAmount.setText(String.valueOf(goal.OverallGoalAmount));
        try {
            m_spGoalAmountType.setSelection(Arrays.asList(m_oController.getWorkoutTypes()).indexOf(goal.OverallGoalAmountType));
        } catch (Exception ex) {
            //quick error checking for now
            //TODO: Have it try to set this after the workout types are loaded. will need to handle scenario where it loads those first before the Goal though
            Tools.ShowToast(getApplicationContext(), "Workout Types not loaded quick enough", Toast.LENGTH_SHORT);
        }
        for(GoalItem goalItem : goal.GoalItems) {
            m_Adapter.add(goalItem);
        }
    }

    @Override
    public void PopulateWorkoutTypeDropDown(WorkoutType[] workoutTypes) {
        ArrayAdapter<WorkoutType> aa = new ArrayAdapter<>(m_spGoalAmountType.getContext(), android.R.layout.simple_spinner_dropdown_item, workoutTypes);
        m_spGoalAmountType.setAdapter(aa);
    }
}
