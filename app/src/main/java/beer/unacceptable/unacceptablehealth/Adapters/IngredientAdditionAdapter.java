package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.Adapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.IngredientAddition;
import beer.unacceptable.unacceptablehealth.R;

public class IngredientAdditionAdapter extends Adapter {
    public IngredientAdditionAdapter(int iLayout, int iDialogLayout) {
        super(iLayout, iDialogLayout, false);
    }

    private Ingredient[] m_dsIngredients;

    @Override
    protected boolean AddItem(Dialog d, boolean bExisting, String sExtraData) {
        Spinner selection = d.findViewById(R.id.ingredient_selector_spinner);
        EditText amount = (EditText) d.findViewById(R.id.ingredient_amount);
        EditText measure = d.findViewById(R.id.ingredient_measure);
        EditText id = d.findViewById(R.id.ingredientAdditionID);

        Ingredient ingred = (Ingredient)selection.getSelectedItem();
        String sID = id.getText().toString();
        double dAmount = Tools.ParseDouble(amount.getText().toString());
        String sMeasure = measure.getText().toString();

        if (ingred == null || dAmount <= 0 || sMeasure.length() == 0) {
            InfoMissing(d.getContext());
            return false;
        }

        IngredientAddition ingredientAddition;

        if (bExisting) {
            ingredientAddition = (IngredientAddition)GetClickedItem();
            ingredientAddition.ingredient = ingred;
            ingredientAddition.amount = dAmount;
            ingredientAddition.measure = sMeasure;
            //ingred.idString = sID;
        } else {
            ingredientAddition = new IngredientAddition(dAmount, sMeasure, ingred);
            //ingred.idString = sID;
            add(ingredientAddition);
        }

        return true;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (OnlyEmptyIngredientExists()) return;



        IngredientAddition i = (IngredientAddition) m_Dataset.get(position);

        TextView txtName = holder.view.findViewById(R.id.ingredient_name);
        TextView txtAmount = holder.view.findViewById(R.id.ingredient_amount);
        TextView txtMeasure = holder.view.findViewById(R.id.ingredient_measure);

        //if (OnlyEmptyIngredientExists()) {

        //} else {
            txtName.setText(i.ingredient.name);
            txtAmount.setText(Double.toString(i.amount));
            txtMeasure.setText(i.measure);
        //}
        //Hop item = (Hop) m_Dataset.get(position);

        //holder.txtHeader.setText(item.name);
        //holder.txtFooter.setText("AAU: " + item.aau);
    }

    @Override
    protected View SetupDialog(final Context c, ListableObject i) {
        View root = super.SetupDialog(c,i);
        if (m_dsIngredients == null || m_dsIngredients.length == 0) return root;

        IngredientAddition ingredientAddition = (IngredientAddition)i;

        Spinner ingredSpinner = root.findViewById(R.id.ingredient_selector_spinner);

        ArrayAdapter<Ingredient> aa = new ArrayAdapter<>(c, android.R.layout.simple_spinner_dropdown_item, m_dsIngredients);
        ingredSpinner.setAdapter(aa);



        /*Ingredient ingred = (Ingredient)i;

        if (ingred != null) {
            ingredID.setText(ingred.idString);
            name.setText(ingred.name);
        }*/

        if (ingredientAddition != null) {
            EditText amount = root.findViewById(R.id.ingredient_amount);
            EditText measure = root.findViewById(R.id.ingredient_measure);

            ingredSpinner.setSelection(GetIngredientPosition(ingredientAddition.ingredient));
            amount.setText(String.valueOf(ingredientAddition.amount));
            measure.setText(ingredientAddition.measure);
        }

        return root;
    }

    private int GetIngredientPosition(Ingredient ingredient) {
        int position = 0;
        for (Ingredient i : m_dsIngredients) {
            if (i.name.equals(ingredient.name)) break;
            position++;
        }

        return position;
    }

    public void PopulateDataset(ArrayList<IngredientAddition>ds) {
        if (ds == null || ds.size() == 0) return;

        for (int i = 0; i < ds.size(); i++) {
            add(ds.get(i));
        }
    }

    public void PopulateIngredients(Ingredient[] ingreds) {
        m_dsIngredients = ingreds;
    }
}
