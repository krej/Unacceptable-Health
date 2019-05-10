package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.AddExercise;
import beer.unacceptable.unacceptablehealth.Screens.MainActivity;

public class ExerciseListAdapterViewControl extends BaseAdapterViewControl {
    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView tvName = view.view.findViewById(R.id.firstLine);

        tvName.setText(i.name);
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        m_Adapter.showAddItemDialog(m_Activity, v.getContext(), SingleItemViewControl.EDIT_ITEM, i);
    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {

    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }

    @Override
    public boolean AddItemUsesActivity() {
        return true;
    }

    @Override
    public Intent SetupNewActivity(Context c, ListableObject i) {
        String id = i == null ? null : i.idString;
        Intent intent = new Intent(c, AddExercise.class);
        intent.putExtra("exercise", id);
        return intent;
    }
}
