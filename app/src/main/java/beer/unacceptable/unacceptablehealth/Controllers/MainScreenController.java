package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.Response;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class MainScreenController extends BaseLogic<MainScreenController.View> {

    private IRepository m_repo;
    private IDateLogic m_date;

    public MainScreenController(IRepository repository, IDateLogic dateLogic) {
        m_repo = repository;
        m_date = dateLogic;
    }

    public void LoadTodaysLog() {
        Date dtToday = m_date.getTodaysDate();
        String sToday = Tools.FormatDate(dtToday, DailyLog.m_sDateFormat);
        m_repo.LoadDailyLogByDate(sToday, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                if (t.contains("Log does not exist.")) {
                    view.showNewLogButton(true);
                    view.showTodaysLog(false);
                } else {
                    DailyLog dl = Tools.convertJsonResponseToObject(t, DailyLog.class);
                    view.showTodaysLog(true);
                    view.showNewLogButton(false);
                    view.populateTodaysLog(dl);
                }
                /*try {
                    DailyLog dl = Tools.convertJsonResponseToObject(t, DailyLog.class);
                } catch (Exception ex) {
                    Response response = Tools.convertJsonResponseToObject(t, Response.class);
                    if (response.Success && response.Message.equals("Log does not exist.")) {
                        view.showNewLogButton(true);
                        view.showTodaysLog(false);
                    }
                }*/
            }

            @Override
            public void onError(VolleyError error) {
                view.showTodaysLog(false);
                view.showNewLogButton(false);
                view.showDailyLogError();
            }
        });
    }

    public interface View {

        void showTodaysLog(boolean b);

        void showNewLogButton(boolean b);

        void populateTodaysLog(DailyLog dl);
        void showDailyLogError();
    }
}
