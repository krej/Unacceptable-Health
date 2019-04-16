package beer.unacceptable.unacceptablehealth.Screens;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Controllers.PerformWorkoutController;
import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.Models.Workout;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

import static beer.unacceptable.unacceptablehealth.Controllers.PerformWorkoutController.ONGOING_NOTIFICATION_ID;

public class PerformWorkout extends BaseActivity implements PerformWorkoutController.View {

    private PerformWorkoutController m_oController;

    LinearLayout m_llWeights;
    LinearLayout m_llReps;
    LinearLayout m_llTime;

    TextView m_tvName;
    TextView m_tvWeights;
    TextView m_tvSets;
    TextView m_tvReps;
    Button m_btnStartWorkoutTimer;
    Chronometer m_chronoWorkout;
    TextView m_tvExerciseDescription;

    Button m_btnFinishSet;

    Button m_btnFinishRest;
    Chronometer m_chronoRest;

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
        //boolean bCancel = getIntent().getBooleanExtra("cancel", false);

        /*if (bCancel) {
            CancelNotification();
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            //System.exit(0);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            //TODO: This will laucnh the main activity even if the app isn't open. I should fix that.
            startActivity(i);
            return;
        }*/


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
        m_llTime = findViewById(R.id.ll_workout_timer);

        m_tvName = findViewById(R.id.tv_workout_name);
        m_tvReps = findViewById(R.id.rep_number);
        m_tvSets = findViewById(R.id.set_number);
        m_tvWeights = findViewById(R.id.weight_number);

        m_btnFinishSet = findViewById(R.id.btn_finish_set);

        m_btnStartWorkoutTimer = findViewById(R.id.workout_timer_button);
        m_chronoWorkout = findViewById(R.id.workout_timer);
        m_tvExerciseDescription = findViewById(R.id.exercise_description);
    }

    private void FindUIElementsForRestView() {
        m_chronoRest = findViewById(R.id.chronometer);
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

        Intent cancelIntent = new Intent(context, PerformWorkout.class);
        cancelIntent.putExtra("cancel", true);
        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //PendingIntent pendingCancelIntent = PendingIntent.getActivity(context, 1, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
                //.addAction(0, getString(R.string.cancel), pendingCancelIntent);

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
        m_llTime.setVisibility(AddExerciseController.getVisibility(exercisePlan.Exercise.ShowTime));

        Tools.SetText(m_tvName, exercisePlan.Exercise.name);
        Tools.SetText(m_tvReps, exercisePlan.Reps);
        Tools.SetText(m_tvSets, exercisePlan.SetsRemainingString());
        Tools.SetText(m_tvWeights, exercisePlan.Weight);
        m_chronoWorkout.setBase(SystemClock.elapsedRealtime() + (exercisePlan.timeInMilliseconds() + ExercisePlan.EXERCISE_LEAD_IN_TIME));
        m_chronoWorkout.setCountDown(true);
        Tools.SetText(m_tvExerciseDescription, exercisePlan.Exercise.Description);
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
        invalidateOptionsMenu();
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
    public void StartRestChronometer(long iBase) {
        m_chronoRest.setBase(iBase);
        m_chronoRest.start();
    }

    @Override
    public void StopChronometer() {
        m_chronoRest.stop();
    }

    @Override
    public void SwitchToWorkoutView() {
        setContentView(R.layout.activity_perform_workout);
        FindUIElementsForWorkoutView();
        SetupWorkoutClickEvents();
        invalidateOptionsMenu();
    }

    private void SetupWorkoutClickEvents() {
        m_btnFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_oController.finishSet();
            }
        });
        m_btnStartWorkoutTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_chronoWorkout.start();
            }
        });
    }

    @Override
    public void CompleteWorkout(Workout workout) {
        Intent intent = new Intent(getApplicationContext(), WorkoutComplete.class);
        intent.putExtra("workout", workout);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perform_workout, menu);
        MenuItem miSave = menu.findItem(R.id.cancel_workout);
        miSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CancelNotification();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            }
        });
        //miSave.setVisible(m_oLogic == null || m_oLogic.canEditLog());
        return true;
    }
}
