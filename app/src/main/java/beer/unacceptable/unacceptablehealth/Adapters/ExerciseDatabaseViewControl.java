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
        Bundle b = new Bundle();

        //TODO: Find a less copy/paste way to do this switch statement
        switch(i.name) {
            case "Workout Types":
                b.putString("collectionName", "workouttype");
                b.putString("title", "Workout Types");
                b.putString("shortName", "Workout Type");
                b.putSerializable("viewControl", new SingleItemViewControl(b.getString("collectionName"), b.getString("shortName")));
                break;
            case "Exercises":
                b = CreateExerciseBundle();
                b.putSerializable("viewControl", new ExerciseListAdapterViewControl());
                break;
            case "Muscles":
                b.putString("collectionName", "muscle");
                b.putString("title", "Muscles");
                b.putString("shortName", "Muscle");
                b.putSerializable("viewControl", new SingleItemViewControl(b.getString("collectionName"), b.getString("shortName")));
                break;
        }

        Intent intent = new Intent(v.getContext(), classToLaunch);

        intent.putExtra("bundle", b);
        v.getContext().startActivity(intent);
    }

    @Override
    public void onItemLongPress(View v, ListableObject i) {
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }

    public static Bundle CreateExerciseBundle() {
        Bundle b = new Bundle();

        b.putString("collectionName", "exercise");
        b.putString("title", "Exercises");
        b.putString("shortName", "Exercise");

        return b;
    }
}
