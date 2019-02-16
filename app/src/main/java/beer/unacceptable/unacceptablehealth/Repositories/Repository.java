package beer.unacceptable.unacceptablehealth.Repositories;

import com.android.volley.Request;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Date;

public class Repository implements IRepository {
    public void LoadDailyLog(String sStringID, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog/" + sStringID, null, callback, true);
    }

    /**
     * Loads a daily log by date
     * @param sDate The date to load. Must be in the format "mm/dd/yyyy". It doesn't NEED to be mm, it could be m, it looks it up by integer values
     * @param callback
     */
    public void LoadDailyLogByDate(String sDate, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog/" + sDate, null, callback, true );
    }
}
