package beer.unacceptable.unacceptablehealth.Tools;

import android.content.Context;
import android.widget.Toast;

public class Tools {
    public static void ShowToast(Context c, CharSequence text, int length) {
        Toast t = Toast.makeText(c, text, length);
        t.show();
    }

    public static int ConvertDptoPixels(float dp, Context c) {
        return (int) (dp * c.getResources().getDisplayMetrics().density + 0.5f);
    }
}
