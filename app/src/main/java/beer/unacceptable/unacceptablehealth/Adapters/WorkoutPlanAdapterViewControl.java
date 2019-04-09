package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.SingleItemList;
import beer.unacceptable.unacceptablehealth.Screens.ViewWorkoutPlan;

public class WorkoutPlanAdapterViewControl extends BaseAdapterViewControl {
    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView textView = view.view.findViewById(R.id.firstLine);
        textView.setText(i.name);
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        m_Adapter.showAddItemDialog(v.getContext(), i);
    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {

    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }

    public boolean AddItemUsesActivity() {
        return true;
    }

    public Intent SetupNewActivity(Context c, ListableObject i) {
        Intent intent = new Intent(c, ViewWorkoutPlan.class);
        intent.putExtra("idString", i == null ? "" : i.idString);
        return intent;
    }
}
