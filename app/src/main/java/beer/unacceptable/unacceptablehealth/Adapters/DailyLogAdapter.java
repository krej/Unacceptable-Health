package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.Adapter;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.FoodRecipe;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.ViewDailyLog;
import beer.unacceptable.unacceptablehealth.Screens.ViewRecipe;

public class DailyLogAdapter extends Adapter {

    public DailyLogAdapter(int iLayout, int iDialogLayout) {
        super(iLayout, iDialogLayout, false);
    }

    @Override
    protected boolean AddItem(Dialog d, boolean bExisting, String sExtraData) {
        return false;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //if (size() == 0) return;

        DailyLog dl = (DailyLog)m_Dataset.get(position);

        TextView etFirstLine = holder.view.findViewById(R.id.firstLine);
        //etFirstLine.setText(Tools.FormatDate(dl.date, DateFormat));
        //etFirstLine.setText("butt sax");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(m_iLayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        DailyLogAdapter.MyViewHolder vh = new DailyLogAdapter.MyViewHolder(v);
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

                    DailyLog dl = (DailyLog)m_Dataset.get(item);

                    Intent i = new Intent(view.getContext(), ViewDailyLog.class);
                    i.putExtra("idString", dl.idString);
                    /*Bundle bundle = new Bundle();
                    bundle.putSerializable("recipe", b);
                    i.putExtras(bundle);*/
                    view.getContext().startActivity(i);

                }
            });
        }
    }
}
