package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.R;


public class ExercisePlanAdapterViewControl extends BaseAdapterViewControl {
    private Exercise[] m_oExercises;

    private Spinner spExercise;
    private EditText etReps;
    private EditText etWeight;
    private EditText etTime;
    private EditText etSets;

    @Override
    public void SetupDialog(View root, ListableObject i) {
        spExercise = root.findViewById(R.id.spExercise);
        etReps = root.findViewById(R.id.reps);
        etSets = root.findViewById(R.id.sets);

        etWeight = root.findViewById(R.id.weight);
        etTime = root.findViewById(R.id.time);

        Tools.PopulateDropDown(spExercise, root.getContext(), m_oExercises);

        ExercisePlan ep = (ExercisePlan)i;

        if (i != null) {
            spExercise.setSelection(Arrays.asList(m_oExercises).indexOf(ep.Exercise));
            etReps.setText(String.valueOf(ep.Reps));
            etSets.setText(String.valueOf(ep.Sets));
            etWeight.setText(String.valueOf(ep.Weight));
            etTime.setText(String.valueOf(ep.Seconds));
        }
    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView textView = view.view.findViewById(R.id.firstLine);
        ExercisePlan ep = (ExercisePlan)i;
        textView.setText(ep.Exercise.name);
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
