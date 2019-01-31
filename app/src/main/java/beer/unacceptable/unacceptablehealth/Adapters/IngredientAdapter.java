package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.Adapter;

import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.IngredientAddition;
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

        TextView txtName = holder.view.findViewById(R.id.firstLine);


        txtName.setText(i.name);


        //Hop item = (Hop) m_Dataset.get(position);

        //holder.txtHeader.setText(item.name);
        //holder.txtFooter.setText("AAU: " + item.aau);
    }
}
