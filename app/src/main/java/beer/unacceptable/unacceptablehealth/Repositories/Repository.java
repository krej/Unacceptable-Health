package beer.unacceptable.unacceptablehealth.Repositories;

import com.android.volley.Request;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

public class Repository implements IRepository {
    public void LoadDailyLog(String sStringID, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog/" + sStringID, null, callback, true);
    }
}
