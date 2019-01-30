package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.Adapter;

import beer.unacceptable.unacceptablehealth.Activities.ViewRecipe;
import beer.unacceptable.unacceptablehealth.Models.*;
import beer.unacceptable.unacceptablehealth.R;

public class RecipeAdapter extends Adapter {
    //private Recipe[] m_Dataset;

    public RecipeAdapter(int iLayout, int iDialogLayout) {
        super(iLayout, iDialogLayout);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(m_iLayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    public class MyViewHolder extends Adapter.ViewHolder {
        public View m_LinearLayout;
        public MyViewHolder(View v) {
            super(v);
            m_LinearLayout = v;

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int item = getLayoutPosition();
                    Recipe b = (Recipe)m_Dataset.get(item);
                    //Tools.ShowToast(view.getContext(), b.name, Toast.LENGTH_LONG);
                    Intent i = new Intent(view.getContext(), ViewRecipe.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recipe", b);
                    i.putExtras(bundle);
                    view.getContext().startActivity(i);
                }
            });
        }
    }
/*
    public RecipeAdapter(Recipe[] myDataset) {
        m_Dataset = myDataset;
    }

    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_preview, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //super(holder, position);
        /*TextView bar_name = holder.m_LinearLayout.findViewById(R.id.bar_name);
        TextView bar_rating = holder.m_LinearLayout.findViewById(R.id.bar_rating);
        LinearLayout keywords = holder.m_LinearLayout.findViewById(R.id.keywords);*/

        TextView recipe_name = holder.view.findViewById(R.id.recipe_name);

        recipe_name.setText(m_Dataset.get(position).name);
    }
        /*bar_name.setText(m_Dataset[position].name);
        bar_rating.setText("Rating: " + m_Dataset[position].rating + " / 5");
*/
        /*for (Keyword k: m_Dataset[position].keywords) {
            AddKeyword(keywords, k);
        }
  /*  }*/

    /*private void AddKeyword(LinearLayout linearLayout, Keyword k) {
        int padding = Tools.ConvertDptoPixels(3, linearLayout.getContext());

        TextView tv = new TextView(linearLayout.getContext());
        tv.setText(k.text);
        tv.setBackground(linearLayout.getContext().getDrawable(k.background));
        tv.setTextColor(Color.WHITE);
        tv.setPadding(padding, padding, padding, padding);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(padding, padding, padding, padding);
        layoutParams.weight = 1;
        tv.setLayoutParams(layoutParams);


        linearLayout.addView(tv);
    }*/

    @Override
    protected boolean AddItem(Dialog d, boolean bExisting, String sExtraData) {
        return false;
    }
}