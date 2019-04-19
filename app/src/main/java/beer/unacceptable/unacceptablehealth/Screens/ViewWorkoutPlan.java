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
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Adapters.ExercisePlanAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.WorkoutPlanController;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewWorkoutPlan extends BaseActivity implements WorkoutPlanController.View {

    WorkoutPlanController m_oController;
    WorkoutType[] m_oWorkoutTypes;

    RecyclerView m_rvExercisePlans;
    NewAdapter m_aExercisePlans;
    ExercisePlanAdapterViewControl m_vcExercisePlan;

    Spinner m_spWorkoutType;
    EditText m_etName;

    String m_IdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout_plan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_aExercisePlans.showAddItemDialog(view.getContext(), null);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FindUIElements();

        m_oController = new WorkoutPlanController(new Repository(), new LibraryRepository());
        m_oController.attachView(this);

        m_vcExercisePlan = new ExercisePlanAdapterViewControl();
        m_aExercisePlans = Tools.setupRecyclerView(m_rvExercisePlans, getApplicationContext(), R.layout.list_exerciseplan, R.layout.dialog_exerciseplan, false, m_vcExercisePlan, true, false, true);

        m_IdString = getIntent().getStringExtra("idString");
        m_oController.LoadWorkoutPlan(m_IdString);
    }

    private void FindUIElements() {
        m_rvExercisePlans = findViewById(R.id.exercisePlanList);
        m_spWorkoutType = findViewById(R.id.spWorkoutType);
        m_etName = findViewById(R.id.name_entry);
    }

    @Override
    public void ShowToast(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void PopulateWorkoutType(WorkoutType[] workoutTypes) {
        Tools.PopulateDropDown(m_spWorkoutType, getApplicationContext(), workoutTypes);
        m_oWorkoutTypes = workoutTypes;
    }

    @Override
    public void PopulateExercises(Exercise[] exercises) {
        m_vcExercisePlan.PopulateExerciseList(exercises);
    }

    @Override
    public void PopulateWorkoutPlan(WorkoutPlan workoutPlan) {
        m_aExercisePlans.clear();
        Tools.PopulateAdapter(m_aExercisePlans, workoutPlan.ExercisePlans);
        m_etName.setText(workoutPlan.name);
        //TODO: I don't know why the below didn't work, so I created that function below for it.
        //int selection = Arrays.asList(m_oWorkoutTypes).indexOf(workoutPlan.WorkoutType);
        int selection = m_oController.getWorkoutTypeSpinnerIndex(m_oWorkoutTypes, workoutPlan.WorkoutType);
        m_spWorkoutType.setSelection(selection);
        //ShowToast(String.valueOf(selection));
    }

    @Override
    public void ClearErrors() {
        m_etName.setError(null);
    }

    @Override
    public void SetNameError(String sMessage) {
        m_etName.setError(sMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_workoutplan, menu);
        MenuItem miSave = menu.findItem(R.id.save_workoutplan);
        MenuItem miStartWorkout = menu.findItem(R.id.perform_workout);

        miSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Save();
                return true;
            }
        });

        miStartWorkout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getApplicationContext(), PerformWorkout.class);
                i.putExtra("idString", m_IdString);
                startActivity(i);
                return true;
            }
        });

        //miSave.setVisible(m_oLogic == null || m_oLogic.canEditLog());
        return true;
    }

    private void Save() {
        //String idString = m_WorkoutPlan == null ? "" : m_WorkoutPlan.idString;
        String sName = m_etName.getText().toString();
        WorkoutType workoutType = (WorkoutType)m_spWorkoutType.getSelectedItem();
        ArrayList<ListableObject> exercisePlans = m_aExercisePlans.Dataset();


        m_oController.Save(sName, workoutType, exercisePlans);
    }
}
