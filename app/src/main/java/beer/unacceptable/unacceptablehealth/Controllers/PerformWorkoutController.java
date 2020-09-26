package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.ITimeSource;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;


import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Models.ExercisePlan;
import beer.unacceptable.unacceptablehealth.Models.Workout;
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
    private long m_lRestStartTime;
    private boolean m_bBetweenExercises;
    private Workout m_oWorkout;

    /**
     * This is used to let me click finish and rest instead of long click when debugging things and I just want to get to the end of it.
     * DO NOT CHECK IN OR USE WITH THIS AS TRUE
     */
    private boolean DEBUGMODE = false;

    public PerformWorkoutController(IRepository repo, ILibraryRepository libraryRepository, ITimeSource timeSource) {
        m_Repo = repo;
        m_LibraryRepo = libraryRepository;
        m_iCurrentExercisePlan = 0;
        m_bIsInRestMode = false;
        m_TimeSource = timeSource;
        m_lStartTime = m_TimeSource.currentTimeMillis();
        m_oWorkout = null;
    }

    public void LoadWorkoutPlan(String idString) {
        m_Repo.LoadWorkoutPlan(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutPlan plan = Tools.convertJsonResponseToObject(t, WorkoutPlan.class, true);
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

    /**
     * Button click to finish the set
     */
    public void finishSet(boolean bFinish) {
        bFinish = DebugFinishVariable(bFinish);

        if (bFinish) {
            m_bIsInRestMode = true;

            view.StopWorkoutChronometer();

            //getCurrentExercisePlan().CompletedSets += 1;
            completeSet(getCurrentExercisePlan());

            if (!CheckForFinishedWorkout()) {

                view.SwitchToRestView();

                m_lRestStartTime = m_TimeSource.currentTimeMillis();
                view.StartRestChronometer(getElapsedTime(m_lRestStartTime));

                ShowNextWorkoutInRestView(m_lRestStartTime);
            }
        } else {
            view.ShowToast(getFinishedButtonText());
        }
    }

    private String getFinishedButtonText() {
        if (DEBUGMODE) {
            return "Single press to continue.";
        }
        return "Long press to continue.";
    }

    /**
     * This inverts the functionality of the 'Finish Set/Rest' buttons, so when debugging you can
     * quickly click it instead of long press.
     */
    private boolean DebugFinishVariable(boolean bFinish) {
        if (DEBUGMODE)
            bFinish = !bFinish;
        return bFinish;
    }

    private void completeSet(ExercisePlan ep) {
        ep.CompletedSets += 1;
        if (ep.CompletedSets >= ep.Sets)
            ep.Completed = true;
    }

    private void ShowNextWorkoutInRestView(long iTime) {
        String sNotificationText = "";

        //TODO: Unit test
        ExercisePlan next = getNextExercisePlan();
        if (getCurrentExercisePlan().SetsRemaining() <= 0 && next != null) {
            view.ShowNextExercise(AddExerciseController.getVisibility(true));
            view.ShowNextWeights(AddExerciseController.getVisibility(next.Exercise.ShowWeight));
            view.PopulateNextExercise(next, getIncompleteExercises());
            sNotificationText = "Next Workout: " + next.name;
            m_bBetweenExercises = true;
        } else {
            view.ShowNextExercise(AddExerciseController.getVisibility(false));
            sNotificationText = "Rest Time - " + getCurrentExercisePlan().toString();
            m_bBetweenExercises = false;
        }

        showNotification(sNotificationText, true, iTime);
    }

    public boolean isBetweenExercises() {
        return m_bBetweenExercises;
    }

    private ExercisePlan[] getIncompleteExercises() {
        ArrayList<ExercisePlan> exercisePlans = new ArrayList<>();

        for (ExercisePlan ep: m_WorkoutPlan.ExercisePlans) {
            if (!ep.Completed)
                exercisePlans.add(ep);
        }

        //TODO: Find a better way to do this than 2 for loops. this is really ugly.
        ExercisePlan[] result = new ExercisePlan[exercisePlans.size()];
        for (int i = 0; i < exercisePlans.size(); i++) {
            result[i] = exercisePlans.get(i);
        }

        return result;
    }

    public void finishRest(ExercisePlan ep, boolean bFinish) {
        bFinish = DebugFinishVariable(bFinish);

        if (bFinish) {
            m_bIsInRestMode = false;

            if (ep != null)
                SetCurrentExercisePlan(ep);

            ExercisePlan exercisePlan = getCurrentExercisePlan();

            if (exercisePlan.CompletedSets >= exercisePlan.Sets) {
                exercisePlan.Completed = true;
                //m_iCurrentExercisePlan++;
            }

            //TODO: Check if you're done with all your exercises and go to the finished screen if so
            view.StopRestChronometer();

            //if (m_iCurrentExercisePlan >= m_WorkoutPlan.ExercisePlans.size()) {
            //if (getIncompleteExercises().length == 0) {
            //    completeWorkout();
            //} else {
            if (!CheckForFinishedWorkout()) {
                view.SwitchToWorkoutView();
                view.PopulateScreenWithExercisePlan(getCurrentExercisePlan());
                showNotification();
            }
        } else {
            view.ShowToast(getFinishedButtonText());
        }
    }

    private boolean CheckForFinishedWorkout() {
        if (getIncompleteExercises().length == 0) {
            completeWorkout();
            return true;
        }
        return false;
    }

    private void SetCurrentExercisePlan(ExercisePlan ep) {
        m_iCurrentExercisePlan = m_WorkoutPlan.ExercisePlans.indexOf(ep);
    }

    private void completeWorkout() {
        view.CancelNotification();

        m_oWorkout = createWorkout();

        view.CompleteWorkout(m_oWorkout);
    }

    private Workout createWorkout() {
        Workout workout = new Workout();
        workout.name = m_WorkoutPlan.name;
        workout.WorkoutPlan = m_WorkoutPlan;
        workout.Date = m_TimeSource.getTodaysDate();
        workout.StartTime = m_lStartTime;
        workout.EndTime = m_TimeSource.currentTimeMillis();

        return workout;
    }

    private ExercisePlan getCurrentExercisePlan() {
        return m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan);
    }
    
    public ExercisePlan getNextExercisePlan() {
        /*if (m_WorkoutPlan.ExercisePlans.size() > m_iCurrentExercisePlan + 1)
            return m_WorkoutPlan.ExercisePlans.get(m_iCurrentExercisePlan + 1);
        */

        ExercisePlan[] exercisePlans = getIncompleteExercises();
        if (exercisePlans.length == 0)
            return null;

        return exercisePlans[0];
    }

    public void showNotification() {
        ExercisePlan exercisePlan = getCurrentExercisePlan();
        showNotification("Current Workout: " + exercisePlan.toString(), false, m_lStartTime);
    }

    public void showNotification(String sNotificationText, boolean bUseChronometer, long iTime) {

        view.ShowNotification(m_WorkoutPlan, m_iCurrentExercisePlan, m_bIsInRestMode, iTime, m_lStartTime, sNotificationText, bUseChronometer);

    }

    public long getRestStartTime() {
        return m_lRestStartTime;
    }

    public void AddExercisePlan(ExercisePlan ep) {
        m_WorkoutPlan.ExercisePlans.add(ep);
        if (m_bIsInRestMode) {
            ShowNextWorkoutInRestView(getRestStartTime());
        }
    }

    public void changeWeight(double dWeight) {
        if (getCurrentExercisePlan().Weight != dWeight) {
            getCurrentExercisePlan().Weight = dWeight;
            m_WorkoutPlan.HasChanges = true;
        }
    }

    public void adjustSet(int iAdjustment) {
        if (getCurrentExercisePlan().Reps + iAdjustment < 0)
            getCurrentExercisePlan().Reps = 0;
        else
            getCurrentExercisePlan().Reps += iAdjustment;

        m_WorkoutPlan.HasChanges = true;

        view.PopulateScreenWithExercisePlan(getCurrentExercisePlan());
    }

    public Workout getWorkout() {
        return m_oWorkout;
    }

    public interface View {
        void ShowNotification(WorkoutPlan workoutPlan, int iCurrentExercise, boolean bInRestMode, long iRestTime, long iStartTime, String sNotificationText, boolean bUseChronometer);
        void CancelNotification();
        void PopulateScreenWithExercisePlan(ExercisePlan exercisePlan);
        void ShowToast(String sMessage);

        void SwitchToRestView();

        void StartRestChronometer(long iTime);
        void StopRestChronometer();

        void SwitchToWorkoutView();
        void CompleteWorkout(Workout workout);

        void ShowNextExercise(int iVisible);

        void PopulateNextExercise(ExercisePlan next, ExercisePlan remainingExercises[]);

        void ShowNextWeights(int visibility);

        void StopWorkoutChronometer();
    }
}
