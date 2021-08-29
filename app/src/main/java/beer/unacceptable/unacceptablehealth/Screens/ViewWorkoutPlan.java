package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Adapters.ExercisePlanAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Adapters.WorkoutHistoryAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.WorkoutPlanController;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.Workout;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewWorkoutPlan extends BaseActivity implements WorkoutPlanController.View {

    WorkoutPlanController m_oController;
    WorkoutType[] m_oWorkoutTypes;
    Exercise[] m_oExercises;

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
        m_oExercises = exercises;
        m_vcExercisePlan.PopulateExerciseList(exercises);
    }

    @Override
    public void PopulateWorkoutPlan(WorkoutPlan workoutPlan) {
        m_aExercisePlans.clear();
        Tools.PopulateAdapter(m_aExercisePlans, workoutPlan.ExercisePlans);
        //m_etName.setText(workoutPlan.name);
        SetName(workoutPlan.name);
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
    public void SetName(String sName) {
        Tools.SetText(m_etName, sName);
    }

    @Override
    public void UpdateAdapter() {
        m_aExercisePlans.notifyDataSetChanged();
    }

    @Override
    public void LoadHistoryScreen(Workout[] workouts) {
        Intent i = new Intent(getApplicationContext(), SingleItemList.class);
        Bundle b = new Bundle();
        //b.putString("collectionName", "muscle");
        b.putString("title", "History: " + m_oController.getWorkoutPlanName());
        b.putSerializable("data", workouts);
        //b.putString("shortName", "Muscle");
        b.putInt("itemLayout", R.layout.list_workout_history);
        b.putSerializable("viewControl", new WorkoutHistoryAdapterViewControl());
        b.putBoolean("addHorizontalSpacing", true);

        i.putExtra("bundle", b);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_workoutplan, menu);
        /*MenuItem miSave = menu.findItem(R.id.save_workoutplan);
        MenuItem miStartWorkout = menu.findItem(R.id.perform_workout);
        MenuItem miCopyToNew = menu.findItem(R.id.copy_to_new_workoutplan);

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
                i.putExtra("exercises", m_oExercises);
                i.putExtra("idString", m_IdString);
                startActivity(i);
                return true;
            }
        });

        miCopyToNew.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                m_oController.CopyToNew();
                return false;
            }
        });
*/
        //miSave.setVisible(m_oLogic == null || m_oLogic.canEditLog());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_workoutplan:
                Save();
                break;
            case R.id.perform_workout:
                Intent i = new Intent(getApplicationContext(), PerformWorkout.class);
                i.putExtra("exercises", m_oExercises);
                i.putExtra("idString", m_IdString);
                startActivity(i);
                break;
            case R.id.copy_to_new_workoutplan:
                m_oController.CopyToNew();
                break;
            case R.id.adjustAllReps:
                m_oController.adjustAllReps(2); //TODO: Have a prompt so I can specify how many reps to adjust by.
                break;
            case R.id.view_workoutplan_history:
                m_oController.loadHistoryScreen();
                break;
            case R.id.view_workoutplan_muscles:
                m_oController.viewMuscleList();
                break;
            case android.R.id.home:
                onBackPressed();;
                break;
        }

        return true;
    }

    @Override
    public void ViewMuscleList(String sMuscleList) {
        //Tools.ShowToast(this, "Test", Toast.LENGTH_LONG);
        Tools.CreateAlertDialog(this, sMuscleList, false);
    }

    private void Save() {
        //String idString = m_WorkoutPlan == null ? "" : m_WorkoutPlan.idString;
        String sName = m_etName.getText().toString();
        WorkoutType workoutType = (WorkoutType)m_spWorkoutType.getSelectedItem();
        ArrayList<ListableObject> exercisePlans = m_aExercisePlans.Dataset();


        m_oController.Save(sName, workoutType, exercisePlans);
    }
}
