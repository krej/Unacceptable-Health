package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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

        etDate.setText(m_oLogic.getLongDate(dl));
        overallRating.setRating(m_oLogic.getDaysAverageRating(dl));

        //etDate.setShadowLayer(1.5f, 1, 1, Color.parseColor("#FFFFFF")); //8f3820
    }

    @Override
    public void onItemClick(View v, ListableObject i) {
        DailyLog dl = (DailyLog)i;
        Intent intent = new Intent(v.getContext(), ViewDailyLog.class);
        intent.putExtra("idString", dl.idString);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity)v.getContext(),

                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<View, String>(v.findViewById(R.id.dailyLogPreview_Date),
                        ViewDailyLog.VIEW_NAME_HEADER_TITLE));

        //v.getContext().startActivity(intent);
        ActivityCompat.startActivity(v.getContext(), intent, activityOptions.toBundle());
    }

    @Override
    public boolean onDialogOkClicked(Dialog d, ListableObject i) {
        return false;
    }

}