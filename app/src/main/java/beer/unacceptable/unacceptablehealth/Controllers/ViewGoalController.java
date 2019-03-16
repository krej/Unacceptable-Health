package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewGoalController extends BaseLogic<ViewGoalController.View> {

    private IRepository m_Repository;
    private WorkoutType[] m_aWorkoutTypes;

    public ViewGoalController(IRepository repository) {
        m_Repository = repository;
    }

    public void loadGoal(String sIdString, final boolean bRefreshing) {
        m_Repository.LoadGoal(sIdString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Goal goal = Tools.convertJsonResponseToObject(t, Goal.class);
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

    public interface View {
        void PopulateScreen(Goal goal, boolean bRefreshing);
        void PopulateWorkoutTypeDropDown(WorkoutType[] workoutTypes);
    }
}
