package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.DailyLogLogic;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.ViewDailyLog;

public class DailyLogAdapterViewControl extends BaseAdapterViewControl {
    DailyLogLogic m_oLogic = new DailyLogLogic(null, null, null);

    public DailyLogAdapterViewControl() {
        m_bAlternateRowColors = true;
        m_sAlternateRowBackgroundColor = "#dbdbdb";
    }

    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        DailyLog dl = (DailyLog)i;

        TextView etDate = view.view.findViewById(R.id.dailyLogPreview_Date);
        RatingBar overallRating = view.view.findViewById(R.id.dailyLogPreview_OverallDayRating);

        etDate.setText(Tools.FormatDate(dl.date, DailyLog.LongDateFormat));
        overallRating.setRating(m_oLogic.getDaysAverageRating(dl));

        etDate.setShadowLayer(1.5f, 1, 1, Color.parseColor("#8f3820"));
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        DailyLog dl = (DailyLog)i;
        Intent intent = new Intent(v.getContext(), ViewDailyLog.class);
        intent.putExtra("idString", dl.idString);
        v.getContext().startActivity(intent);
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }

}