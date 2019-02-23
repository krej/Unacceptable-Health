package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.WorkoutTypeList;


public class ExerciseDatabaseViewControl extends BaseAdapterViewControl {
    public ExerciseDatabaseViewControl() {
        m_bAlternateRowColors = true;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView tv = view.view.findViewById(R.id.firstLine);
        tv.setText(i.name);
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        Class<?> classToLaunch = null;
        switch(i.name) {
            case "Workout Types":
                classToLaunch = WorkoutTypeList.class;
                break;
        }

        if (classToLaunch != null) {
            Intent intent = new Intent(v.getContext(), classToLaunch);
            v.getContext().startActivity(intent);
        }
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }
}
