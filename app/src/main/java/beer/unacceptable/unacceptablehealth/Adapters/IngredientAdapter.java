package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.Adapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.R;

public class IngredientAdapter extends Adapter {
    public IngredientAdapter(int iLayout, int iDialogLayout) {
        super(iLayout, iDialogLayout);
    }

    @Override
    protected boolean AddItem(Dialog d, boolean bExisting, String sExtraData) {
        return false;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (OnlyEmptyIngredientExists()) return;



        Ingredient i = (Ingredient) m_Dataset.get(position);

        TextView txtName = holder.view.findViewById(R.id.ingredient_name);
        TextView txtAmount = holder.view.findViewById(R.id.ingredient_amount);
        TextView txtMeasure = holder.view.findViewById(R.id.ingredient_measure);

        txtName.setText(i.name);
        txtAmount.setText(Double.toString(i.amount));
        txtMeasure.setText(i.measure);

        //Hop item = (Hop) m_Dataset.get(position);

        //holder.txtHeader.setText(item.name);
        //holder.txtFooter.setText("AAU: " + item.aau);
    }

    public void PopulateDataset(ListableObject[] ds) {
        for (int i = 0; i < ds.length; i++) {
            add(ds[i]);
        }
    }
}
