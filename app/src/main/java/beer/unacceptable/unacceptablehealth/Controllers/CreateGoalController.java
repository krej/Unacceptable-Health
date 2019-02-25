package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.Goal;
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

    public CreateGoalController(CreateGoalController.View view, IDateLogic dateLogic, IRepository repository) {
        attachView(view);
        m_DateLogic = dateLogic;
        m_repository = repository;
        m_oGoal = new Goal();
        m_oPendingGoalItems = new ArrayList<>();
    }


    public void setDate(Calendar cal, DateType dateType) {
        cal.set(Calendar.HOUR_OF_DAY, 12); //hard code to noon to avoid timezone crap
        String sDate = Tools.FormatDate(cal.getTime(), DailyLog.LongDateFormat);

        switch (dateType) {
            case Start:
                m_oGoal.StartDate = cal.getTime();
                view.setStartDateText(sDate);
                break;
            case End:
                m_oGoal.EndDate = cal.getTime();
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

    public WorkoutType[] getWorkoutTypes() {
        return m_oWorkoutTypes;
    }

    public interface View {
        void setStartDateText(String sDate);
        void setEndDateText(String sDate);

        void setGoalItems(ArrayList<PendingGoalItem> eq);

        void clearGoalItems();
        void sendWorkoutTypesToAdapter(WorkoutType[] types);
    }
}
