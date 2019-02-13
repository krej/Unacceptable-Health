package beer.unacceptable.unacceptablehealth.Logic;

import android.widget.CheckBox;
import android.widget.RadioButton;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static beer.unacceptable.unacceptablehealth.Adapters.DailyLogAdapter.DateFormat;

public class DailyLogLogic extends BaseLogic<DailyLogLogic.View> {
    IRepository repository;
    IDateLogic dateLogic;

    DailyLog m_dlLog;

    public DailyLogLogic(IRepository repository, IDateLogic dateLogic) {
        this.repository = repository;
        this.dateLogic = dateLogic;
    }

    public void LoadLog(String idString) {
        if (Tools.IsEmptyString(idString)) {
            createNewDailyLog();
            view.fillScreen(m_dlLog);
            view.setScreenControlsEnabled(true);
            return;
        }

        repository.LoadDailyLog(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                m_dlLog = gson.fromJson(response, DailyLog.class);
                view.setScreenTitle("Daily Log: " + Tools.FormatDate(m_dlLog.date, "MMM dd yy"));
                view.fillScreen(m_dlLog);

                if (m_dlLog.date == null || Tools.CompareDatesWithoutTime(m_dlLog.date,dateLogic.getTodaysDate())) {
                    view.setScreenControlsEnabled(true);
                } else {
                    view.setScreenControlsEnabled(false);
                }
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
        if (canEditLog(sDate)) {
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

            m_dlLog.Save();
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

    //TODO: Make this accept a Date object, might be easier to compare
    private boolean canEditLog(String sDate) {
        return sDate.equalsIgnoreCase(Tools.FormatDate(dateLogic.getTodaysDate(), "MM/dd/yyyy")) || sDate.length() == 0;
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

    private void createNewDailyLog() {
        view.setScreenTitle("Create Daily Log");
        m_dlLog = new DailyLog();
        m_dlLog.date = dateLogic.getTodaysDate();
    }

    public interface View {
        void setScreenTitle(String sTitle);
        void fillScreen(DailyLog log);
        void showMessage(String sMessage);
        void setScreenControlsEnabled(boolean bEnabled);
    }
}
