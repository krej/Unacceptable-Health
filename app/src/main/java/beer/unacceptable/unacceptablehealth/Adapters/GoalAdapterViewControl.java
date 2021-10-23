package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.ViewGoal;

public class GoalAdapterViewControl extends BaseAdapterViewControl {
    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView name = view.view.findViewById(R.id.goal_name);
        TextView duration = view.view.findViewById(R.id.goal_duration);
        TextView goalsCompleted = view.view.findViewById(R.id.goal_items_completed);
        //TextView goalsCompletedPercent = view.view.findViewById(R.id.goal_items_completed_percent);
        TextView goalsCompletedPercent = view.view.findViewById(R.id.goalPercentComplete);
        TextView goalItemsCompletedLabel = view.view.findViewById(R.id.goal_items_completed_label);

        Goal g = (Goal)i;

        name.setText(g.name);
        duration.setText(g.DurationLabel());
        //TODO: Make bCountRestDays a setting
        goalsCompleted.setText(g.GoalsCompletedLabel(false));
        goalsCompletedPercent.setText(g.GoalsCompletedPercent(false, true));
        goalsCompletedPercent.setTextColor(g.GetGoalsCompletedPercentColor(false, view.view.getContext(), new DateLogic()));
        Tools.SetText(goalItemsCompletedLabel, g.GetGoalItemsCompletedLabel());
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        Intent intent = new Intent(v.getContext(), ViewGoal.class);
        intent.putExtra("id", i.idString);
        v.getContext().startActivity(intent);
    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }
}
