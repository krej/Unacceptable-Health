package beer.unacceptable.unacceptablehealth.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.IAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Screens.ViewDailyLog;

public class DailyLogAdapterViewControl extends BaseAdapterViewControl {
    @Override
    public void SetupDialog(View root, ListableObject i) {

    }

    @Override
    public void SetupViewInList(NewAdapter.ViewHolder view, ListableObject i) {
        DailyLog dl = (DailyLog)i;

        TextView etFirstLine = view.view.findViewById(R.id.firstLine);
        etFirstLine.setText(Tools.FormatDate(dl.date, DailyLog.LongDateFormat));
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