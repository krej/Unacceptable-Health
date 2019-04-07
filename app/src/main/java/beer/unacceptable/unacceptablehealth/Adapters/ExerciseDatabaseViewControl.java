package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.IAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.AddExercise;
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
        Class<?> classToLaunch = SingleItemList.class;
        String sCollectionName = "";
        String sTitle = "";
        String sShortName = "";
        BaseAdapterViewControl viewControl = null;

        //TODO: Find a less copy/paste way to do this switch statement
        switch(i.name) {
            case "Workout Types":
                sCollectionName = "workouttype";
                sTitle = "Workout Types";
                sShortName = "Workout Type";
                viewControl = new SingleItemViewControl(sCollectionName, sShortName);
                break;
            case "Exercises":
                sCollectionName = "exercise";
                sTitle = "Exercises";
                sShortName = "Exercise";
                viewControl = new ExerciseListAdapterViewControl();
                break;
            case "Muscles":
                sCollectionName = "muscle";
                sTitle = "Muscles";
                sShortName = "Muscle";
                viewControl = new SingleItemViewControl(sCollectionName, sShortName);
                break;
        }

        if (sCollectionName.length() > 0 && viewControl != null) {
            Intent intent = new Intent(v.getContext(), classToLaunch);
            intent.putExtra("collectionName", sCollectionName);
            intent.putExtra("title", sTitle);
            intent.putExtra("shortName", sShortName);
            intent.putExtra("viewControl", viewControl);
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
