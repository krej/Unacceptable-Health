package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Controllers.AddExerciseController;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;


public class ExercisePlanAdapterViewControl extends BaseAdapterViewControl {
    private AddExerciseController m_oController;
    private Exercise[] m_oExercises;

    private Spinner spExercise;
    private EditText etReps;
    private EditText etWeight;
    private EditText etTime;
    private EditText etSets;
    private LinearLayout llDialogWeight;
    private LinearLayout llDialogTime;
    private LinearLayout llDialogReps;

    public ExercisePlanAdapterViewControl() {
        m_oController = new AddExerciseController(new Repository(), new LibraryRepository());
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {
        spExercise = root.findViewById(R.id.spExercise);
        etReps = root.findViewById(R.id.reps);
        llDialogReps = root.findViewById(R.id.ll_dialog_reps);
        etSets = root.findViewById(R.id.sets);

        etWeight = root.findViewById(R.id.weight);
        llDialogWeight = root.findViewById(R.id.ll_dialog_weight);
        etTime = root.findViewById(R.id.time);
        llDialogTime = root.findViewById(R.id.ll_dialog_time);

        Tools.PopulateDropDown(spExercise, root.getContext(), m_oExercises);
        ExerciseDropDownSelectionEvent();

        ExercisePlan ep = (ExercisePlan)i;

        if (i != null) {
            spExercise.setSelection(Arrays.asList(m_oExercises).indexOf(ep.Exercise));
            etReps.setText(String.valueOf(ep.Reps));
            etReps.setVisibility(m_oController.getVisibility(ep.Exercise.ShowReps));
            etSets.setText(String.valueOf(ep.Sets));

            etWeight.setText(String.valueOf(ep.Weight));
            llDialogWeight.setVisibility(m_oController.getVisibility(ep.Exercise.ShowWeight));


            etTime.setText(String.valueOf(ep.Seconds));
            llDialogTime.setVisibility(m_oController.getVisibility(ep.Exercise.ShowTime));

        }
    }

    private void ExerciseDropDownSelectionEvent() {
        spExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < m_oExercises.length) {
                    Exercise e = m_oExercises[position];
                    llDialogWeight.setVisibility(m_oController.getVisibility(e.ShowWeight));
                    llDialogTime.setVisibility(m_oController.getVisibility(e.ShowTime));
                    llDialogReps.setVisibility(m_oController.getVisibility(e.ShowReps));
                    m_oController.ClearValueBasedOnVisibility(etTime, e.ShowTime);
                    m_oController.ClearValueBasedOnVisibility(etWeight, e.ShowWeight);
                    m_oController.ClearValueBasedOnVisibility(etReps, e.ShowReps);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView tvExerciseName = view.view.findViewById(R.id.exerciseName);
        TextView tvReps = view.view.findViewById(R.id.rep_number);
        TextView tvSets = view.view.findViewById(R.id.set_number);
        TextView tvWeight = view.view.findViewById(R.id.weight_number);
        TextView tvTime = view.view.findViewById(R.id.time_number);

        LinearLayout llReps = view.view.findViewById(R.id.ll_reps);
        LinearLayout llWeight = view.view.findViewById(R.id.ll_weight);
        LinearLayout llTime = view.view.findViewById(R.id.ll_time);

        ExercisePlan ep = (ExercisePlan)i;
        tvExerciseName.setText(ep.Exercise.name);

        Tools.SetText(tvReps, ep.Reps);
        llWeight.setVisibility(m_oController.getVisibility(ep.Exercise.ShowReps));

        Tools.SetText(tvSets, ep.Sets);

        Tools.SetText(tvWeight, ep.Weight);
        llWeight.setVisibility(m_oController.getVisibility(ep.Exercise.ShowWeight));

        Tools.SetText(tvTime, ep.Seconds);
        llTime.setVisibility(m_oController.getVisibility(ep.Exercise.ShowTime));


    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        m_Adapter.showAddItemDialog(v.getContext(), i);
    }

    @Override
    public void onItemLongPress(View v, final ListableObject i) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

        String[] options = new String[1];

        options[0] = "Delete";

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Exercise Plan Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        m_Adapter.remove(i);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        boolean bNewItem = i == null;

        Exercise e = (Exercise)spExercise.getSelectedItem();
        int iReps = Tools.ParseInt(etReps);
        int iSets = Tools.ParseInt(etSets);
        double dWeight = Tools.ParseDouble(etWeight);
        int iTime = Tools.ParseInt(etTime);


        ExercisePlan exercisePlan = (ExercisePlan)i;
        if (bNewItem) {
            exercisePlan = new ExercisePlan();
        }

        exercisePlan.Exercise = e;
        exercisePlan.name = e.name;
        exercisePlan.Reps = iReps;
        exercisePlan.Sets = iSets;
        exercisePlan.Weight = dWeight;
        exercisePlan.Seconds = iTime;

        if (bNewItem) m_Adapter.add(exercisePlan);
        return true;
    }

    public void PopulateExerciseList(Exercise[] exercises) {
        m_oExercises = exercises;
    }
}
