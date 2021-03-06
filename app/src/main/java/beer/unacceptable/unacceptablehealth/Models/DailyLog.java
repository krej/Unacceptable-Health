package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import org.apache.commons.codec.binary.Base64;

import java.util.Date;

public class DailyLog extends ListableObject {
    public static String m_sDateFormat = "MM/dd/yyyy";
    public static String LongDateFormat = "E, MMMM dd, yyyy";

    /**
     * The date of the entry
     */
    @Expose
    public Date date;

    /**
     * How healthy you felt today
     */
    @Expose
    public int HealthRating;

    /**
     * BBD...
     */
    @Expose
    public boolean BBD;


    /**
     * Did you use flonase today?
     */
    @Expose
    public boolean UsedFlonase;

    /**
     * The reasoning for using flonase:
     * 1. Allergies were bad
     * 2. Allergies were looking to be bad, so I took it to be safe
     * 3. I had something I needed to do today, so I took it as a precaution even though I felt fine. Allergy Insurance
     */
    @Expose
    public int FlonaseReasoning;

    /**
     * Did you have a headache?
     */
    @Expose
    public boolean HadHeadache;

    /**
     * Did you have to work today?
     */
    @Expose
    public boolean WorkDay;

    /**
     * How good was your work day? 1-10, 10 was great
     */
    @Expose
    public int WorkRating;

    /**
     * How good was the non-working part of your day?
     */
    @Expose
    public int PersonalDayRating;

    /**
     * Explain a mindful moment.
     * I'm not sure if i'm going to keep this but maybe it'll be cool. We did it in therapy.
     */
    @Expose
    public String MindfulMoment;

    /**
     * Overall notes about the day. Anything exciting happen? Any profound thoughts? Etc
     */
    @Expose
    public String OverallNotes;

    public String formatDate() {
        return Tools.FormatDate(date, m_sDateFormat);
    }

    public boolean isFlonaseBadAllergy() {
        return FlonaseReasoning == 1;
    }

    public boolean isFlonaseStartingAllergies() {
        return FlonaseReasoning == 2;
    }

    public boolean isFlonaseAllergyInsurance() {
        return FlonaseReasoning == 3;
    }

    public String getMindfulMoment() {
        return Tools.decodeBase64(MindfulMoment);
    }

    public String getOverallNotes() {
        return Tools.decodeBase64(OverallNotes);
    }

}
