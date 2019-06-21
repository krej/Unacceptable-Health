package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.Workout;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class WorkoutComplete extends BaseActivity implements MainScreenController.View {
    private MainScreenController m_oController;
    private GoalItem[] m_oGoalItems;
    private Button btnCompleteWorkout;
    private TextView m_tvDuration;
    private Workout m_Workout;

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
        for (GoalItem goalItem : m_oGoalItems) {
            if (goalItem.WorkoutType.idString.equals(workout.WorkoutPlan.WorkoutType.idString)) {
                m_oController.ToggleGoalItemComplete(goalItem, null);
            }
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
    public void populateTodaysGoalItems(GoalItem[] goalItems) {
        m_oGoalItems = goalItems;
        //TODO: This doesn't belong here but I just want to get it working.
        btnCompleteWorkout.setEnabled(true);
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
}
