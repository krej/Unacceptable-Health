package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.ITimeSource;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;


import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class PerformWorkoutController extends BaseLogic<PerformWorkoutController.View> {

    public static int ONGOING_NOTIFICATION_ID = 2;

    private IRepository m_Repo;
    private ILibraryRepository m_LibraryRepo;
    private int m_iCurrentExercisePlan;
    private WorkoutPlan m_WorkoutPlan;
    private boolean m_bIsInRestMode;
    private long m_lStartTime; //TODO: store this in a workout object
    private ITimeSource m_TimeSource;

    public PerformWorkoutController(IRepository repo, ILibraryRepository libraryRepository, ITimeSource timeSource) {
        m_Repo = repo;
        m_LibraryRepo = libraryRepository;
        m_iCurrentExercisePlan = 0;
        m_bIsInRestMode = false;
        m_TimeSource = timeSource;
        m_lStartTime = m_TimeSource.currentTimeMillis();
    }

    public void LoadWorkoutPlan(String idString) {
        m_Repo.LoadWorkoutPlan(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutPlan plan = Tools.convertJsonResponseToObject(t, WorkoutPlan.class);
                m_WorkoutPlan = plan;
                view.PopulateScreenWithExercisePlan(m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan));
                showNotification();
            }

            @Override
            public void onError(VolleyError error) {
                view.ShowToast(Tools.ParseVolleyError(error));
            }
        });
    }


    public void LoadWorkoutPlan(WorkoutPlan workoutPlan, boolean bIsInRestMode, int iCurrentExercise, long iTime, long iStartTime) {
        m_WorkoutPlan = workoutPlan;
        m_bIsInRestMode = bIsInRestMode;
        m_iCurrentExercisePlan = iCurrentExercise;
        m_lStartTime = iStartTime;

        if (bIsInRestMode) {
            view.SwitchToRestView();
            //start chronometer
            view.StartRestChronometer(getElapsedTime(iTime));
            ShowNextWorkoutInRestView(iTime);
        } else {
            view.SwitchToWorkoutView();
            view.PopulateScreenWithExercisePlan(getCurrentExercisePlan());
            //showNotification();
        }
    }

    private long getElapsedTime(long iStartTime) {
        long iOffset = m_TimeSource.currentTimeMillis() - iStartTime;
        return m_TimeSource.elapsedRealtime() - iOffset;
    }

    public void finishSet() {
        m_bIsInRestMode = true;

        getCurrentExercisePlan().CompletedSets += 1;

        view.SwitchToRestView();

        long now = m_TimeSource.currentTimeMillis();
        view.StartRestChronometer(getElapsedTime(now));

        ShowNextWorkoutInRestView(now);
    }

    private void ShowNextWorkoutInRestView(long iTime) {
        String sNotificationText = "";

        //TODO: Unit test
        ExercisePlan next = getNextExercisePlan();
        if (getCurrentExercisePlan().SetsRemaining() <= 0 && next != null) {
            view.ShowNextExercise(AddExerciseController.getVisibility(true));
            view.ShowNextWeights(AddExerciseController.getVisibility(next.Exercise.ShowWeight));
            view.PopulateNextExercise(next);
            sNotificationText = "Next Workout: " + next.name;
        } else {
            view.ShowNextExercise(AddExerciseController.getVisibility(false));
            sNotificationText = "Rest Time - " + getCurrentExercisePlan().toString();
        }

        showNotification(sNotificationText, true, iTime);
    }

    public void finishRest() {
        m_bIsInRestMode = false;

        ExercisePlan exercisePlan = getCurrentExercisePlan();

        if (exercisePlan.CompletedSets >= exercisePlan.Sets) {
            exercisePlan.Completed = true;
            m_iCurrentExercisePlan++;
        }

        //TODO: Check if you're done with all your exercises and go to the finished screen if so
        view.StopChronometer();

        if (m_iCurrentExercisePlan >= m_WorkoutPlan.ExercisePlans.size()) {
            completeWorkout();
        } else {
            view.SwitchToWorkoutView();
            view.PopulateScreenWithExercisePlan(getCurrentExercisePlan());
            showNotification();
        }

    }

    private void completeWorkout() {
        view.CancelNotification();
        view.CompleteWorkout();
    }

    private ExercisePlan getCurrentExercisePlan() {
        return m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan);
    }
    
    private ExercisePlan getNextExercisePlan() {
        if (m_WorkoutPlan.ExercisePlans.size() > m_iCurrentExercisePlan + 1)
            return m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan + 1);
        
        return null;
    }

    public void showNotification() {
        ExercisePlan exercisePlan = getCurrentExercisePlan();
        showNotification("Current Workout: " + exercisePlan.toString(), false, m_lStartTime);
    }

    public void showNotification(String sNotificationText, boolean bUseChronometer, long iTime) {

        view.ShowNotification(m_WorkoutPlan, m_iCurrentExercisePlan, m_bIsInRestMode, iTime, m_lStartTime, sNotificationText, bUseChronometer);

    }



    public interface View {
        void ShowNotification(WorkoutPlan workoutPlan, int iCurrentExercise, boolean bInRestMode, long iRestTime, long iStartTime, String sNotificationText, boolean bUseChronometer);
        void CancelNotification();
        void PopulateScreenWithExercisePlan(ExercisePlan exercisePlan);
        void ShowToast(String sMessage);

        void SwitchToRestView();

        void StartRestChronometer(long iTime);
        void StopChronometer();

        void SwitchToWorkoutView();
        void CompleteWorkout();

        void ShowNextExercise(int iVisible);

        void PopulateNextExercise(ExercisePlan next);

        void ShowNextWeights(int visibility);
    }
}
