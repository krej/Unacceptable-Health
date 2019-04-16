package beer.unacceptable.unacceptablehealth.Screens;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.TimeSource;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.AddExerciseController;
import beer.unacceptable.unacceptablehealth.Controllers.PerformWorkoutController;
import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

import static beer.unacceptable.unacceptablehealth.Controllers.PerformWorkoutController.ONGOING_NOTIFICATION_ID;

public class PerformWorkout extends BaseActivity implements PerformWorkoutController.View {

    private PerformWorkoutController m_oController;

    LinearLayout m_llWeights;
    LinearLayout m_llReps;
    //TODO: I'm missing time

    TextView m_tvName;
    TextView m_tvWeights;
    TextView m_tvSets;
    TextView m_tvReps;

    Button m_btnFinishSet;

    Button m_btnFinishRest;
    Chronometer m_Chronometer;

    LinearLayout m_llNextWorkout;
    LinearLayout m_llNextWeight;
    TextView m_tvNextWeight;
    TextView m_tvNextWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwitchToWorkoutView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //keep the screen on when you're working out
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FindUIElementsForWorkoutView();

        m_oController = new PerformWorkoutController(new Repository(), new LibraryRepository(), new TimeSource());
        m_oController.attachView(this);

        String idString = getIntent().getStringExtra("idString");
        WorkoutPlan workoutPlan = (WorkoutPlan)getIntent().getSerializableExtra("workout");
        boolean bIsInRestMode = getIntent().getBooleanExtra("isInRestMode", false);
        int iCurrentExercise = getIntent().getIntExtra("currentExercise", -1);
        long iTime = getIntent().getLongExtra("restTime", System.currentTimeMillis());
        long iStartTime = getIntent().getLongExtra("startTime", System.currentTimeMillis());

        if (workoutPlan == null) {
            m_oController.LoadWorkoutPlan(idString);
        } else {
            m_oController.LoadWorkoutPlan(workoutPlan, bIsInRestMode, iCurrentExercise, iTime, iStartTime);
        }

        //m_oController.startWorkoutNotification(this.getBaseContext());
        //m_oController.showNotification(this.getBaseContext());

    }

    private void FindUIElementsForWorkoutView() {
        m_llReps = findViewById(R.id.ll_reps);
        m_llWeights = findViewById(R.id.ll_weight);

        m_tvName = findViewById(R.id.tv_workout_name);
        m_tvReps = findViewById(R.id.rep_number);
        m_tvSets = findViewById(R.id.set_number);
        m_tvWeights = findViewById(R.id.weight_number);

        m_btnFinishSet = findViewById(R.id.btn_finish_set);

    }

    private void FindUIElementsForRestView() {
        m_Chronometer = findViewById(R.id.chronometer);
        m_btnFinishRest = findViewById(R.id.btn_finishRest);
        m_llNextWorkout = findViewById(R.id.ll_next_workout);
        m_tvNextWeight = findViewById(R.id.next_weight_number);
        m_tvNextWorkout = findViewById(R.id.tv_next_workout_name);
        m_llNextWeight = findViewById(R.id.ll_next_weight);
    }

    @Override
    public void ShowNotification(WorkoutPlan workoutPlan, int iCurrentExercise, boolean bInRestMode, long iRestTime, long iStartTime, String sNotificationText, boolean bUseChronometer) {
        Context context = getBaseContext();
        //long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, PerformWorkout.class);
        notificationIntent.putExtra("workout", workoutPlan);
        notificationIntent.putExtra("currentExercise", iCurrentExercise);
        notificationIntent.putExtra("isInRestMode", bInRestMode);
        notificationIntent.putExtra("restTime", iRestTime);
        notificationIntent.putExtra("startTime", iStartTime);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.NOTIFICATION_CHANNEL_ID))
                .setSmallIcon(R.drawable.ic_dumbbell)
                .setContentTitle("Workout: " + workoutPlan.name)
                .setContentText(sNotificationText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setWhen(iRestTime)
                .setUsesChronometer(bUseChronometer)
                .setVibrate(new long [] {1000, 1000, 1000})
                .setAutoCancel(false)
                .setOngoing(true);

        notificationManager.notify(ONGOING_NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public void CancelNotification() {
        NotificationManager nm = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(ONGOING_NOTIFICATION_ID);
    }

    @Override
    public void PopulateScreenWithExercisePlan(ExercisePlan exercisePlan) {
        m_llWeights.setVisibility(AddExerciseController.getVisibility(exercisePlan.Exercise.ShowWeight));
        m_llReps.setVisibility(AddExerciseController.getVisibility(exercisePlan.Exercise.ShowReps));

        Tools.SetText(m_tvName, exercisePlan.Exercise.name);
        Tools.SetText(m_tvReps, exercisePlan.Reps);
        Tools.SetText(m_tvSets, exercisePlan.SetsRemainingString());
        Tools.SetText(m_tvWeights, exercisePlan.Weight);

    }

    @Override
    public void ShowToast(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void SwitchToRestView() {
        setContentView(R.layout.activity_perform_workout_restview);
        FindUIElementsForRestView();
        SetupRestClickEvents();
    }

    private void SetupRestClickEvents() {
        m_btnFinishRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_oController.finishRest();
            }
        });
    }

    @Override
    public void StartChronometer(long iBase) {
        m_Chronometer.setBase(iBase);
        m_Chronometer.start();
    }

    @Override
    public void StopChronometer() {
        m_Chronometer.stop();
    }

    @Override
    public void SwitchToWorkoutView() {
        setContentView(R.layout.activity_perform_workout);
        FindUIElementsForWorkoutView();
        SetupWorkoutClickEvents();
    }

    private void SetupWorkoutClickEvents() {
        m_btnFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_oController.finishSet();
            }
        });
    }

    @Override
    public void CompleteWorkout() {
        Intent intent = new Intent(getApplicationContext(), WorkoutComplete.class);
        startActivity(intent);
    }

    @Override
    public void ShowNextExercise(int iVisible) {
        m_llNextWorkout.setVisibility(iVisible);
    }

    @Override
    public void PopulateNextExercise(ExercisePlan next) {
        Tools.SetText(m_tvNextWorkout, next.Exercise.name);
        Tools.SetText(m_tvNextWeight, next.Weight);
    }

    @Override
    public void ShowNextWeights(int visibility) {
        m_llNextWeight.setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        //don't do anything so i don't accidentally hit back and close the app
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("test", "test");
        super.onSaveInstanceState(savedInstanceState);
    }*/

    @Override
    public Context getMyContext() {
        return getApplicationContext();
    }

}
