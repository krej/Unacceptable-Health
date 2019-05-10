package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;

import java.io.Serializable;

import beer.unacceptable.unacceptablehealth.Controllers.SingleItemListController;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class SingleItemViewControl extends BaseAdapterViewControl {
    public static final int ADD_ITEM = 1;
    public static final int EDIT_ITEM = 2;

    private SingleItemListController m_oController;
    private String m_sTitle;

    public SingleItemViewControl(String sCollectionName, String sTitle) {
        m_bAlternateRowColors = false;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
        m_oController = new SingleItemListController(new Repository(), new LibraryRepository(), sCollectionName);
        m_sTitle = sTitle;
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {
        TextView header = root.findViewById(R.id.add_ingredient_header);
        EditText name = root.findViewById(R.id.ingredient_name);


        if (i != null) {
            header.setText("Edit " + m_sTitle);
            name.setText(i.name);
        } else {
            header.setText("Add " + m_sTitle);
        }
    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        TextView name = view.view.findViewById(R.id.firstLine);
        name.setText(i.name);
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        m_Adapter.showAddItemDialog(m_Activity, v.getContext(), EDIT_ITEM, i);
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
