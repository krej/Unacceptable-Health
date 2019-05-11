package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.PerformWorkout;

public class ExerciseSelectionViewControl extends ExercisePlanAdapterViewControl {
    Exercise m_SelectedExercise;


    @Override
    public void SetupDialog(View root, ListableObject i) {
        super.SetupDialog(root, i);

        //spExercise.setSelection(Arrays.asList(m_SelectedExercise).indexOf(m_SelectedExercise));
        spExercise.setSelection(Tools.FindPositionInArray(m_oExercises, m_SelectedExercise));
        spExercise.setEnabled(false);

        LinearLayout llExercise = root.findViewById(R.id.exercisePlanDialog_ExerciseSpinner);
        //llExercise.setVisibility(View.GONE);
    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView name = view.view.findViewById(R.id.firstLine);
        name.setText(i.name);
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        m_SelectedExercise = getExercise(i.idString);
        if (m_SelectedExercise == null) return;

        m_Adapter.showAddItemDialog(v.getContext(), null);
    }

    private Exercise getExercise(String idString) {
        for (Exercise e : m_oExercises) {
            if (e.idString.equals(idString))
                return e;
        }

        return null;
    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {

    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {

        int iReps = Tools.ParseInt(etReps);
        int iSets = Tools.ParseInt(etSets);
        double dWeight = Tools.ParseDouble(etWeight);
        int iTime = Tools.ParseInt(etTime);

        ExercisePlan ep = new ExercisePlan();
        ep.name = m_SelectedExercise.name;
        ep.Exercise = m_SelectedExercise;
        ep.Reps = iReps;
        ep.Sets = iSets;
        ep.Weight = dWeight;
        ep.Seconds = iTime;

        Intent intent = new Intent();
        intent.putExtra("exercisePlan", ep);
        m_Activity.setResult(Activity.RESULT_OK, intent);

        m_Activity.finish();

        return true;
    }
}
