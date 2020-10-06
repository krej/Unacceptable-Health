package beer.unacceptable.unacceptablehealth.Screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Adapters.MuscleAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Adapters.SingleItemViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.AddExerciseController;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.Muscle;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class AddExercise extends BaseActivity implements AddExerciseController.View {

    private RecyclerView m_rvMuscles;
    private NewAdapter m_adpMuscles;
    private AddExerciseController m_oController;
    private MuscleAdapterViewControl m_muscleAdapterViewControl;
    private Button m_btnAddMuscle;
    private EditText m_etName;
    private Switch m_swShowWeight;
    private Switch m_swShowTime;
    private Switch m_swShowReps;
    private EditText m_etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_oController = new AddExerciseController(new Repository(), new LibraryRepository());
        m_oController.attachView(this);

        FindUIElements();

        m_muscleAdapterViewControl = new MuscleAdapterViewControl();
        m_adpMuscles = Tools.setupRecyclerView(m_rvMuscles, getApplicationContext(), R.layout.list_dropdown_with_remove_button, 0, false, m_muscleAdapterViewControl, true, true);

        //m_oController.LoadMuscles();
        SetupButtonClickEvents();

        String idString = getIntent().getStringExtra("exercise");
        m_oController.LoadExercise(idString);
    }

    private void FindUIElements() {
        m_rvMuscles = findViewById(R.id.muscleList);
        m_btnAddMuscle = findViewById(R.id.addMuscle);
        m_etName = findViewById(R.id.name_entry);
        m_swShowTime = findViewById(R.id.swShowTime);
        m_swShowWeight = findViewById(R.id.sw_showWeight);
        m_swShowReps = findViewById(R.id.sw_showReps);
        m_etDescription = findViewById(R.id.exercise_description_text);
    }

    private void SetupButtonClickEvents() {
        m_btnAddMuscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Muscle muscle = new Muscle();
                muscle.name = "Starting Muscle";
                m_adpMuscles.add(muscle);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                m_oController.FinishEditing();
                break;
            case R.id.save:
                Save();
                break;
            case R.id.delete:
                DeletePrompt();
                break;
        }

        return true;
    }

    private void DeletePrompt() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //yes
                        m_oController.DeleteExercise(getIntent().getIntExtra("requestCode", SingleItemViewControl.EDIT_ITEM));
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //no
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddExercise.this);
        builder.setMessage("Delete? Are you sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    private void Save() {
        //String idString = m_Exercise == null ? "" : m_Exercise.idString;
        String sName = m_etName.getText().toString();
        ArrayList<ListableObject> muscles = m_adpMuscles.Dataset();
        boolean bShowWeight = m_swShowWeight.isChecked();
        boolean bShowTime = m_swShowTime.isChecked();
        boolean bShowReps = m_swShowReps.isChecked();
        String sDescription = m_etDescription.getText().toString();

        m_oController.Save(sName, muscles, bShowWeight, bShowTime, bShowReps, sDescription);
    }

    @Override
    public void ShowToast(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void PopulateMuscleList(Muscle[] muscles) {
        m_muscleAdapterViewControl.PopulateMuscleList(muscles);
        m_btnAddMuscle.setEnabled(m_oController.AddMuscleButtonEnabled(muscles));
    }

    @Override
    public void SetNameError(String sError) {
        m_etName.setError(sError);
    }

    @Override
    public void ClearErrors() {
        m_etName.setError(null);
    }

    @Override
    public void setScreenTitle(String sTitle) {
        setTitle(sTitle);
    }

    @Override
    public void PopulateScreen(Exercise exercise) {
        m_etName.setText(exercise.name);
        Tools.PopulateAdapter(m_adpMuscles, exercise.Muscles);
        m_swShowWeight.setChecked(exercise.ShowWeight);
        m_swShowTime.setChecked(exercise.ShowTime);
        m_swShowReps.setChecked(exercise.ShowReps);
        Tools.SetText(m_etDescription, exercise.Description);
    }

    @Override
    public void GoToPreviousActivity(String idString, boolean bDeleted, String sName, boolean bCancel) {
        if (bCancel) {
            setResult(RESULT_CANCELED);
        } else {
            Intent intent = new Intent();
            intent.putExtra("IDString", idString);
            intent.putExtra("name", sName);
            intent.putExtra("deleted", bDeleted);
            setResult(RESULT_OK, intent);
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        m_oController.FinishEditing();

        super.onBackPressed();
    }
}
