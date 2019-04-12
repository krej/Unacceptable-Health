package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;


import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class PerformWorkoutController extends BaseLogic<PerformWorkoutController.View> {

    private IRepository m_Repo;
    private ILibraryRepository m_LibraryRepo;
    private int m_iCurrentExercisePlan;
    private WorkoutPlan m_WorkoutPlan;

    public PerformWorkoutController(IRepository repo, ILibraryRepository libraryRepository) {
        m_Repo = repo;
        m_LibraryRepo = libraryRepository;
        m_iCurrentExercisePlan = 0;

    }

    public void LoadWorkoutPlan(String idString) {
        m_Repo.LoadWorkoutPlan(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutPlan plan = Tools.convertJsonResponseToObject(t, WorkoutPlan.class);
                m_WorkoutPlan = plan;
                view.PopulateScreenWithExercisePlan(m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan));
            }

            @Override
            public void onError(VolleyError error) {
                view.ShowToast(Tools.ParseVolleyError(error));
            }
        });
    }

    public void finishSet() {
        getCurrentExercisePlan().CompletedSets += 1;
        view.SwitchToRestView();
        view.StartChronometer();
    }

    public void finishRest() {
        ExercisePlan exercisePlan = getCurrentExercisePlan();
        if (exercisePlan.CompletedSets >= exercisePlan.Sets) {
            exercisePlan.Completed = true;
            m_iCurrentExercisePlan++;
        }

        //TODO: Check if you're done with all your exercises and go to the finished screen if so
        view.StopChronometer();

        if (m_iCurrentExercisePlan >= m_WorkoutPlan.ExercisePlans.size()) {
            view.CompleteWorkout();
        } else {
            view.SwitchToWorkoutView();
            view.PopulateScreenWithExercisePlan(getCurrentExercisePlan());
        }
    }

    private ExercisePlan getCurrentExercisePlan() {
        return m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan);
    }


    public interface View {
        void PopulateScreenWithExercisePlan(ExercisePlan exercisePlan);
        void ShowToast(String sMessage);

        void SwitchToRestView();

        void StartChronometer();
        void StopChronometer();

        void SwitchToWorkoutView();
        void CompleteWorkout();
    }
}
