package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static com.unacceptable.unacceptablelibrary.Tools.Tools.convertJsonResponseToObject;

public class DailyLogLogic extends BaseLogic<DailyLogLogic.View> {
    IRepository m_repository;
    ILibraryRepository libraryRepository; //for ListableObject saving
    IDateLogic m_DateLogic;

    DailyLog m_dlLog;

    public DailyLogLogic(IRepository repository, IDateLogic dateLogic, ILibraryRepository libraryRepository) {
        this.m_repository = repository;
        this.m_DateLogic = dateLogic;
        this.libraryRepository = libraryRepository;
    }

    public void LoadLog(String idString) {
        if (Tools.IsEmptyString(idString)) {
            createNewDailyLog();
            view.fillScreen(m_dlLog);
            view.setScreenControlsEnabled(true);
            return;
        }

        m_repository.LoadDailyLog(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String response) {
                m_dlLog = convertJsonResponseToObject(response, DailyLog.class);
                //view.setScreenTitle("Daily Log: " + Tools.FormatDate(m_dlLog.date, "MMM dd yy"));
                view.setScreenTitle(getLongDate(m_dlLog));
                view.fillScreen(m_dlLog);

                view.setScreenControlsEnabled(canEditLog());
            }

            @Override
            public void onError(VolleyError error) {
                String errorMsg = "";
                try {
                    errorMsg = error.getCause().getMessage();
                } catch(Exception e) {

                }

                view.showMessage("Failed to load daily logs: " + errorMsg);
                view.setScreenControlsEnabled(false);
            }
        });

    }

    public boolean canEditLog() {
        return m_dlLog == null || m_dlLog.date == null || Tools.CompareDatesWithoutTime(m_dlLog.date,m_DateLogic.getTodaysDate());
    }

    //TODO: Need to handle this returning a Response object
    /*private DailyLog convertJsonResponseToObject(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, DailyLog.class);
    }*/

    public void saveLog(String sDate,
                        int iHealthRating,
                        boolean bBBD,
                        boolean bUsedFlonase,
                        boolean bBadAllergy,
                        boolean bStartingAllergies,
                        boolean bAllergyInsurance,
                        boolean bHadHeadache,
                        boolean bWorkDay,
                        int iWorkRating,
                        int iPersonalDayRating,
                        String sMindfulMoment,
                        String sOverallNotes) {

        Date dt = stringToDate(sDate);
        //if (canEditLog(sDate)) {
        if (canEditLog(dt)) {
            m_dlLog.name = sDate;
            m_dlLog.date = dateAtNoon(dt);
            m_dlLog.HealthRating = iHealthRating;
            m_dlLog.BBD = bBBD;
            m_dlLog.UsedFlonase = bUsedFlonase;
            m_dlLog.FlonaseReasoning = GetFlonaseReasoning(bUsedFlonase, bBadAllergy, bStartingAllergies, bAllergyInsurance);
            m_dlLog.HadHeadache = bHadHeadache;
            m_dlLog.WorkDay = bWorkDay;
            m_dlLog.WorkRating = getWorkRating(bWorkDay, iWorkRating);
            m_dlLog.PersonalDayRating = iPersonalDayRating;
            m_dlLog.MindfulMoment = sMindfulMoment;
            m_dlLog.OverallNotes = sOverallNotes;

            m_dlLog.Save(libraryRepository);
        } else {
            view.showMessage("Log is read only.");
        }
    }

    private Date dateAtNoon(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.setTimeZone(TimeZone.getTimeZone("CDT"));


        return cal.getTime();
    }

    private int getWorkRating(boolean bWorkDay, int iWorkRating) {
        if (bWorkDay) return iWorkRating;
        return 0;
    }

    private int GetFlonaseReasoning(boolean bUsedFlonase, boolean bFlonaseBadAllergy, boolean bFlonaseStartingAllergies, boolean bFlonaseAllergyInsurance) {
        if ( !bUsedFlonase ) return 0;
        if (bFlonaseBadAllergy) return 1;
        if (bFlonaseStartingAllergies) return 2;
        if (bFlonaseAllergyInsurance) return 3;
        return 0;
    }

    private boolean canEditLog(Date dt) {
        Date dtToday = Tools.setTimeToMidnight(m_DateLogic.getTodaysDate());
        return dt.compareTo(dtToday) == 0;
        //return false;
    }

    private Date stringToDate(String sDate) {
        java.text.DateFormat dtFormat = new SimpleDateFormat(DailyLog.m_sDateFormat, Locale.ENGLISH);
        Date dt = null;
        try {
            dt = dtFormat.parse(sDate);
        } catch ( ParseException ex) {
            if (m_dlLog.date == null)
                dt = new Date();
        }

        return dt;
    }

    public DailyLog getLog() {
        return m_dlLog;
    }

    private void createNewDailyLog() {
        view.setScreenTitle("Create Daily Log");
        m_dlLog = new DailyLog();
        m_dlLog.date = m_DateLogic.getTodaysDate();
    }

    public void LoadTodaysLog(final RepositoryCallback callback) {
        String sDate = Tools.FormatDate(m_DateLogic.getTodaysDate(), DailyLog.m_sDateFormat);
        m_repository.LoadDailyLogByDate(sDate, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                m_dlLog = convertJsonResponseToObject(t, DailyLog.class);
                callback.onSuccess(t);
            }

            @Override
            public void onError(VolleyError error) {
                //TODO: ??? what do you do on an error?
                callback.onError(error);
            }
        });
    }

    public boolean continueToLog() {
        if (m_dlLog == null || m_dlLog.idString == null || m_dlLog.idString.length() == 0) return true;

        return !logIsFullyCompleted(m_dlLog);
    }

    private boolean logIsFullyCompleted(DailyLog log) {
        return log.HealthRating > 0 && log.PersonalDayRating > 0 && log.OverallNotes.length() > 0;
    }

    public float getDaysAverageRating(DailyLog log) {
        if (!log.WorkDay) return log.PersonalDayRating;
        return ((float)log.WorkRating + (float)log.PersonalDayRating) / 2;
    }

    public String getLongDate(DailyLog dl) {
        return Tools.FormatDate(dl.date, DailyLog.LongDateFormat);
    }

    public interface View {
        void setScreenTitle(String sTitle);
        void fillScreen(DailyLog log);
        void showMessage(String sMessage);
        void setScreenControlsEnabled(boolean bEnabled);
    }
}
