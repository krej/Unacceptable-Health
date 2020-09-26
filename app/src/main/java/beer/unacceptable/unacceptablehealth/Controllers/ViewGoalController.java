package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Calendar;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalExtension;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewGoalController extends BaseLogic<ViewGoalController.View> {

    private IRepository m_Repository;
    private IDateLogic m_DateLogic;
    private ILibraryRepository m_LibraryRepo;
    private WorkoutType[] m_aWorkoutTypes;
    private Goal m_oGoal;

    public ViewGoalController(IRepository repository, IDateLogic dateLogic, ILibraryRepository libraryRepository) {
        m_Repository = repository;
        m_DateLogic = dateLogic;
        m_LibraryRepo = libraryRepository;
    }

    public void loadGoal(String sIdString, final boolean bRefreshing) {
        m_Repository.LoadGoal(sIdString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Goal goal = Tools.convertJsonResponseToObject(t, Goal.class);
                m_oGoal = goal;
                view.PopulateScreen(goal, bRefreshing);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void loadWorkoutTypes() {
        m_Repository.LoadAllWorkoutTypes(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                m_aWorkoutTypes = Tools.convertJsonResponseToObject(t, WorkoutType[].class);
                view.PopulateWorkoutTypeDropDown(m_aWorkoutTypes);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public WorkoutType[] getWorkoutTypes() {
        return m_aWorkoutTypes;
    }

    public void extendGoal(Calendar c) {
        Date dtStart = Tools.addDays(m_oGoal.EndDate, 1);
        Date dtEnd = c.getTime();
        CreateGoalController oCreateGoalController = new CreateGoalController(null, m_DateLogic, m_Repository, m_LibraryRepo);

        oCreateGoalController.createGoalItems(dtStart, dtEnd, m_oGoal.BasedOnWeek, m_oGoal, m_oGoal.PendingGoalItems);

        GoalExtension goalExtension = new GoalExtension();
        goalExtension.OriginalEndDate = m_oGoal.EndDate;
        goalExtension.NewEndDate = dtEnd;

        m_oGoal.addGoalExtension(goalExtension);
        m_oGoal.EndDate = dtEnd;

        m_oGoal.Save(m_LibraryRepo);

        view.PopulateScreen(m_oGoal, false);
        view.ShowToast("Goal Extended");
    }

    public Goal getGoal() {
        return m_oGoal;
    }

    public void extendGoal() {
        view.ShowCalendar();
    }

    public interface View {
        void PopulateScreen(Goal goal, boolean bRefreshing);
        void PopulateWorkoutTypeDropDown(WorkoutType[] workoutTypes);
        void ShowCalendar();
        void ShowToast(String sMessage);
    }
}
