package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.SingleItemList;


public class ExerciseDatabaseViewControl extends BaseAdapterViewControl {
    public ExerciseDatabaseViewControl() {
        m_bAlternateRowColors = false;
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
        String sCollectionName = "";
        switch(i.name) {
            case "Workout Types":
                classToLaunch = SingleItemList.class;
                sCollectionName = "workouttype";
                break;
            case "Exercises":
                break;
            case "Muscles":
                classToLaunch = SingleItemList.class;
                sCollectionName = "muscle";
                break;
        }

        if (classToLaunch != null && sCollectionName.length() > 0) {
            Intent intent = new Intent(v.getContext(), classToLaunch);
            intent.putExtra("collectionName", sCollectionName);
            v.getContext().startActivity(intent);
        }
    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }
}
