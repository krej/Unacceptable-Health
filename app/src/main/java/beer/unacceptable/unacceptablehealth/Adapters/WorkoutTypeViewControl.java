package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;

import beer.unacceptable.unacceptablehealth.Controllers.WorkoutTypeController;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class WorkoutTypeViewControl extends BaseAdapterViewControl {
    private WorkoutTypeController m_oController;
    public WorkoutTypeViewControl() {
        m_bAlternateRowColors = true;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
        m_oController = new WorkoutTypeController(new Repository(), new LibraryRepository());
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {
        TextView header = root.findViewById(R.id.add_ingredient_header);
        EditText name = root.findViewById(R.id.ingredient_name);

        header.setText("Workout Type");

        if (i != null) {
            name.setText(i.name);
        }
    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView name = view.view.findViewById(R.id.firstLine);
        name.setText(i.name);
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
        EditText name = d.findViewById(R.id.ingredient_name);
        String sName = name.getText().toString();

        return m_oController.save(d.getContext(), m_Adapter, i, sName);
    }
}
