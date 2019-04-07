package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.Models.Muscle;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;

public class MuscleAdapterViewControl extends BaseAdapterViewControl {
    private Muscle[] m_aMuscles;

    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, final ListableObject i) {
        final Muscle m = (Muscle)i;

        Spinner spMuscle = view.view.findViewById(R.id.dropdown);
        Button btnRemove = view.view.findViewById(R.id.btn_remove);

        ArrayAdapter<Muscle> aa = new ArrayAdapter<>(view.view.getContext(), android.R.layout.simple_spinner_dropdown_item, m_aMuscles);
        spMuscle.setAdapter(aa);

        spMuscle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m.name = m_aMuscles[position].name;
                m.idString = m_aMuscles[position].idString;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Adapter.remove(i);
            }
        });
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

    public void PopulateMuscleList(Muscle[] muscles) {
        m_aMuscles = muscles;
    }
}
