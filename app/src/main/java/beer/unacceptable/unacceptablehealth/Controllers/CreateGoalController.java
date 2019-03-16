package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.PendingGoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class CreateGoalController extends BaseLogic<CreateGoalController.View> {

    public enum DateType {
        Start,
        End
    }

    public static String days[] = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private Goal m_oGoal;
    private ArrayList<PendingGoalItem> m_oPendingGoalItems;
    private ArrayList<PendingGoalItem> m_oOldGoalItems;
    private WorkoutType m_oWorkoutTypes[];

    private IDateLogic m_DateLogic;
    private IRepository m_repository;
    private ILibraryRepository m_LibraryRepository;

    public CreateGoalController(CreateGoalController.View view, IDateLogic dateLogic, IRepository repository, ILibraryRepository libraryRepository) {
        attachView(view);
        m_DateLogic = dateLogic;
        m_repository = repository;
        m_LibraryRepository = libraryRepository;
        m_oGoal = new Goal();
        m_oPendingGoalItems = new ArrayList<>();
    }


    public void setDate(Calendar cal, DateType dateType) {
        cal.set(Calendar.HOUR_OF_DAY, 12); //hard code to noon to avoid timezone crap
        Date dt = cal.getTime();
        //Date dt = Tools.setTimeToMidnightUTC(cal.getTime());
        String sDate = Tools.FormatDate(dt, DailyLog.LongDateFormat);

        switch (dateType) {
            case Start:
                m_oGoal.StartDate = dt;
                view.setStartDateText(sDate);
                break;
            case End:
                m_oGoal.EndDate = dt;
                view.setEndDateText(sDate);
                break;
        }
    }

    public void setName(String sName) {
        m_oGoal.name = sName;
    }

    public void changeGoalType(boolean bCreatedBasedOnWeek) {
        if (bCreatedBasedOnWeek) {
            m_oOldGoalItems = new ArrayList<>(m_oPendingGoalItems);

            if (m_oPendingGoalItems.size() == 0) {
                createWeekBasedGoals();
            } else {
                for (int i = 0; i < m_oPendingGoalItems.size() && i < days.length; i++ ) {
                    m_oPendingGoalItems.get(i).Day = days[i];
                }

                for (int i = m_oPendingGoalItems.size(); i < days.length; i++) {
                    PendingGoalItem p = new PendingGoalItem();
                    p.Day = days[i];
                    m_oPendingGoalItems.add(p);
                }

                for (int i = m_oPendingGoalItems.size()-1; i >= days.length; i--)
                    m_oPendingGoalItems.remove(i);
            }
        } else {
            m_oPendingGoalItems = new ArrayList<>(m_oOldGoalItems);

        }

        view.setGoalItems(m_oPendingGoalItems);
    }

    private void createWeekBasedGoals() {
        for (String s : days) {
            PendingGoalItem p = new PendingGoalItem();
            p.Day = s;
            p.WorkoutType = m_oWorkoutTypes[0];
            m_oPendingGoalItems.add(p);
        }
    }

    public Goal getGoal() {
        return m_oGoal;
    }

    public ArrayList<PendingGoalItem> getPendingGoalItems() {
        return m_oPendingGoalItems;
    }

    public void setPendingGoalItems(ArrayList<PendingGoalItem> pendingGoalItems) {
        m_oPendingGoalItems = new ArrayList<>(pendingGoalItems);
        /*m_oPendingGoalItems = new ArrayList<>();
        for(ListableObject i : pendingGoalItems) {
            m_oPendingGoalItems.add((PendingGoalItem)i);
        }*/
    }

    public void setPendingGoalItemsFromAdapter(ArrayList<ListableObject> pendingGoalItems) {
        ArrayList<PendingGoalItem> p = new ArrayList<>();
        /*for(PendingGoalItem i : pendingGoalItems) {
            p.add(i);
        }*/
        for (int i = 0; i < pendingGoalItems.size(); i++) {
            PendingGoalItem item = (PendingGoalItem)pendingGoalItems.get(i);
            p.add(item);
        }

        setPendingGoalItems(p);
    }

    public void loadWorkoutTypes() {
        m_repository.LoadAllWorkoutTypes(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                m_oWorkoutTypes = Tools.convertJsonResponseToObject(t, WorkoutType[].class);
                view.sendWorkoutTypesToAdapter(m_oWorkoutTypes);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void createGoalItems(boolean bBasedOnWeek) {
        Date dt = m_oGoal.StartDate;
        Date dtEnd = addDays(m_oGoal.EndDate,1);
        m_oGoal.GoalItems = new ArrayList<>();
        int iWorkoutCounter = 0;

        while (dt.before(dtEnd)) {

            if (bBasedOnWeek) {
                ArrayList<PendingGoalItem> pendingGoalItems = getPendingGoalItemsForDay(m_oPendingGoalItems, Tools.FormatDate(dt, "EEEE")); //EEEE is the day of week spelled out fully: https://knowm.org/get-day-of-week-from-date-object-in-java/
                for(PendingGoalItem p : pendingGoalItems) {
                    GoalItem g = new GoalItem(dt, p.WorkoutType);
                    m_oGoal.GoalItems.add(g);
                }
            } else {
                GoalItem g = new GoalItem(dt, m_oPendingGoalItems.get(iWorkoutCounter % m_oPendingGoalItems.size()).WorkoutType);
                iWorkoutCounter++;
                m_oGoal.GoalItems.add(g);
            }


            dt = addDays(dt, 1);
        }
    }

    private ArrayList<PendingGoalItem> getPendingGoalItemsForDay(ArrayList<PendingGoalItem> pendingGoalItems, String sDayOfWeek) {
        ArrayList<PendingGoalItem> pgiByday = new ArrayList<>();
        for (PendingGoalItem p : pendingGoalItems) {
            if (p.Day.equals(sDayOfWeek)) {
                pgiByday.add(p);
            }
        }

        return pgiByday;
    }

    private Date addDays(Date dt, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }


    public ArrayList<GoalItem> getGoalItems() {
        return m_oGoal.GoalItems;
    }

    public WorkoutType[] getWorkoutTypes() {
        return m_oWorkoutTypes;
    }


    public void saveGoal(String sName, String sDescription, boolean bBasedOnWeek, ArrayList<ListableObject> dataset, double dGoalAmount, WorkoutType wtGoalType) {
        boolean bContinue = true;
        view.clearErrors();

        if (Tools.IsEmptyString(sName)) {
            view.showNameError();
            bContinue = false;
        }

        if (Tools.IsEmptyString(sDescription)) {
            view.showDescriptionError();
            bContinue = false;
        }

        //hard code don't allow dates before November 1st 2018 because thats when I started creating these types of goals
        if ( m_oGoal.StartDate == null || m_oGoal.StartDate.before(Tools.createDate(Calendar.NOVEMBER, 1, 2018, 0, 0, 0).getTime())) {
            view.showStartDateError();
            bContinue = false;
        }

        if (m_oGoal.EndDate == null) {
            view.showEndDateError("This field is required");
            bContinue = false;
        }

        if (m_oGoal.EndDate != null && m_oGoal.EndDate.before(m_oGoal.StartDate)) {
            view.showEndDateError("End Date must come after Start Date");
            bContinue = false;
        }

        if (dataset == null || dataset.size() == 0) {
            view.showMessage("Pending Goal Items Required");
            bContinue = false;
        }

        if (!bContinue) return;

        m_oGoal.name = sName;
        m_oGoal.Description = sDescription;
        createGoalItems(bBasedOnWeek);
        m_oGoal.BasedOnWeek = bBasedOnWeek;
        m_oGoal.PendingGoalItems = m_oPendingGoalItems;
        m_oGoal.Acheived = false;
        m_oGoal.OverallGoalAmount = dGoalAmount;
        m_oGoal.OverallGoalAmountType = wtGoalType;

        m_oGoal.Save(m_LibraryRepository);
    }

    public interface View {
        void setStartDateText(String sDate);
        void setEndDateText(String sDate);

        void setGoalItems(ArrayList<PendingGoalItem> eq);

        void clearGoalItems();
        void sendWorkoutTypesToAdapter(WorkoutType[] types);
        void showNameError();
        void showDescriptionError();
        void showStartDateError();
        void showEndDateError(String sMessage);
        void showMessage(String sMessage);
        void clearErrors();
    }
}
