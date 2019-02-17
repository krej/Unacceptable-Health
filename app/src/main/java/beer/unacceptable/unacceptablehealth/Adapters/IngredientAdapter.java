package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.Adapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.IngredientAddition;
import beer.unacceptable.unacceptablehealth.R;

public class IngredientAdapter extends Adapter {
    public IngredientAdapter(int iLayout, int iDialogLayout) {
        super(iLayout, iDialogLayout);
    }

    @Override
    protected boolean AddItem(Dialog d, boolean bExisting, String sExtraData) {
        EditText name = d.findViewById(R.id.ingredient_name);
        EditText ingredID = (EditText) d.findViewById(R.id.ingredientID);

        String sName = name.getText().toString();
        String sID = ingredID.getText().toString();

        if (sName.length() == 0) {
            InfoMissing(d.getContext());
            return false;
        }

        Ingredient ingred;

        if (bExisting) {
            ingred = (Ingredient)GetClickedItem();
            ingred.name = sName;
            ingred.idString = sID;
        } else {
            ingred = new Ingredient(sName);
            ingred.idString = sID;
            add(ingred);
        }

        ingred.Save();

        return true;
    }

    @Override
    protected View SetupDialog(final Context c, ListableObject i) {
        View root = super.SetupDialog(c,i);

        /*EditText name = root.findViewById(R.id.ingredient_name);
        EditText ingredID = root.findViewById(R.id.ingredientID);

        Ingredient ingred = (Ingredient)i;

        if (ingred != null) {
            ingredID.setText(ingred.idString);
            name.setText(ingred.name);
        }*/

        return root;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        /*if (OnlyEmptyIngredientExists()) return;



        Ingredient i = (Ingredient) m_Dataset.get(position);

        TextView txtName = holder.view.findViewById(R.id.firstLine);


        txtName.setText(i.name);*/


        //Hop item = (Hop) m_Dataset.get(position);

        //holder.txtHeader.setText(item.name);
        //holder.txtFooter.setText("AAU: " + item.aau);
    }

}
