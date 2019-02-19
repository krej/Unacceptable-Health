package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;

import beer.unacceptable.unacceptablehealth.Logic.IngredientLogic;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.R;

public class IngredientAdapterViewControl extends BaseAdapterViewControl {
    private IngredientLogic m_oIngredientLogic;

    public IngredientAdapterViewControl() {
         m_oIngredientLogic = new IngredientLogic(new LibraryRepository());
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {
        Ingredient ingred = (Ingredient)i;

        EditText name = root.findViewById(R.id.ingredient_name);
        EditText ingredID = root.findViewById(R.id.ingredientID);

        ingredID.setText(ingred.idString);
        name.setText(ingred.name);
    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i, int position) {
        TextView txtName = view.view.findViewById(R.id.firstLine);
        txtName.setText(i.name);
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        m_Adapter.showAddItemDialog(v.getContext(), i);
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        EditText name = d.findViewById(R.id.ingredient_name);
        EditText ingredID = (EditText) d.findViewById(R.id.ingredientID);

        String sName = name.getText().toString();
        String sID = ingredID.getText().toString();

        return m_oIngredientLogic.save(d.getContext(), m_Adapter, i, sName);
    }
}
