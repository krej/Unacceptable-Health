package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.PerformWorkoutController;
import beer.unacceptable.unacceptablehealth.Controllers.WorkoutPlanController;
import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.R;

public class WorkoutSummaryViewControl extends BaseAdapterViewControl {
    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView firstLine = view.view.findViewById(R.id.exerciseName);
        TextView secondLine = view.view.findViewById(R.id.exerciseStatus);

        ExercisePlan exercisePlan = (ExercisePlan)i;

        Tools.SetText(firstLine, exercisePlan.name);
        Tools.SetText(secondLine, exercisePlan.getStatus());

        view.view.setBackgroundColor(PerformWorkoutController.GetExercisePlanBackgroundColor(exercisePlan));
    }

    @Override
    public void onItemClick(View v, ListableObject i) {

    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {

    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }
}
