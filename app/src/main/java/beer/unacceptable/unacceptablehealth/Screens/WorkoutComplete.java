package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.Workout;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class WorkoutComplete extends BaseActivity implements MainScreenController.View {
    private MainScreenController m_oController;
    private GoalItem[] m_oGoalItems;
    private Button btnCompleteWorkout;
    private TextView m_tvDuration;
    private Workout m_Workout;
    private Goal m_Goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_complete);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_Workout = (Workout)getIntent().getSerializableExtra("workout");

        m_oController = new MainScreenController(new Repository(), new DateLogic());
        m_oController.attachView(this);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        m_tvDuration = findViewById(R.id.tv_duration);
        btnCompleteWorkout = findViewById(R.id.btn_CompleteWorkout);
        btnCompleteWorkout.setEnabled(false);
        btnCompleteWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndCompleteWorkout(m_Workout);
                goToMainScreen();
            }
        });

        m_oController.LoadTodaysGoalItems();
        m_oController.LoadCurrentGoal();

        Button btnCancel = findViewById(R.id.cancel_workout);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });
    }

    private void goToMainScreen() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void saveAndCompleteWorkout(Workout workout) {
        if (m_oGoalItems != null) {
            for (GoalItem goalItem : m_oGoalItems) {
                if (goalItem.WorkoutType.idString.equals(workout.WorkoutPlan.WorkoutType.idString) && !goalItem.Completed) {
                    m_oController.ToggleGoalItemComplete(goalItem, null);
                }
            }
        }

        if (m_Goal.Freestyle) {
            GoalItem goalItem = m_oController.CreateNewGoalItem(workout);
            m_Goal.AddGoalItem(goalItem);
            m_Goal.Save();
        }

        workout.Save();

        if (workout.WorkoutPlan.HasChanges) {
            workout.WorkoutPlan.Save();
        }
    }

    @Override
    public void showTodaysLog(boolean b) {

    }

    @Override
    public void showNewLogButton(boolean b) {

    }

    @Override
    public void populateTodaysLog(DailyLog dl) {

    }

    @Override
    public void showDailyLogError() {

    }

    @Override
    public void populateTodaysGoalItems(GoalItem[] goalItems, WorkoutPlan[] plans) {
        m_oGoalItems = goalItems;
        Tools.SetText(m_tvDuration, m_Workout.DurationInMinutes());
    }

    @Override
    public void setGoalItemsVisibility(boolean bVisible) {

    }

    @Override
    public void setNoGoalLabelVisibility(boolean bVisible) {

    }

    @Override
    public void showToast(String sMessage) {

    }

    @Override
    public void enableCompleteWorkoutButton(boolean bEnabled) {
        btnCompleteWorkout.setEnabled(bEnabled);
    }

    @Override
    public void SetGoal(Goal goal) {
        m_Goal = goal;
    }
}
