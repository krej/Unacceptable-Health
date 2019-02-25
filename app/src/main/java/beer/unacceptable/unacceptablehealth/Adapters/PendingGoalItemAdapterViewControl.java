package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Controllers.CreateGoalController;
import beer.unacceptable.unacceptablehealth.Models.PendingGoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;

public class PendingGoalItemAdapterViewControl extends BaseAdapterViewControl {
    private WorkoutType[] m_oWorkouTypes;
    private boolean m_bShowDay;

    public PendingGoalItemAdapterViewControl() {
        m_bAlternateRowColors = true;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
        m_bShowDay = false;
    }
    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        final PendingGoalItem p = (PendingGoalItem)i;

        Spinner spDay = view.view.findViewById(R.id.dropdown_day);
        Spinner spWorkoutType = view.view.findViewById(R.id.dropdown_workouttype);
        SetItemSelectedListeners(view, p, spDay, spWorkoutType);

        spDay.setSelection(Arrays.asList(CreateGoalController.days).indexOf(p.Day));
        if (p.Type != null)
            spWorkoutType.setSelection(Arrays.asList(m_oWorkouTypes).indexOf(p.Type));

        if (m_bShowDay)
            spDay.setVisibility(View.VISIBLE);
        else
            spDay.setVisibility(View.GONE);
    }

    private void SetItemSelectedListeners(NewAdapter.ViewHolder view, final PendingGoalItem p, Spinner spDay, Spinner spWorkoutType) {
        ArrayAdapter<WorkoutType> aa = new ArrayAdapter<>(view.view.getContext(), android.R.layout.simple_spinner_dropdown_item, m_oWorkouTypes);
        spWorkoutType.setAdapter(aa);
        //spDay.setEnabled(false);

        spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p.Day = CreateGoalController.days[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spWorkoutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p.Type = m_oWorkouTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemClick(View v, ListableObject i) {

    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }

    public void setWorkoutTypes(WorkoutType[] types) {
        m_oWorkouTypes = types;
    }

    public void setDayVisibility(boolean bVisible) {
        m_bShowDay = bVisible;
    }
}
