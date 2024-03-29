package beer.unacceptable.unacceptablehealth.Repositories;

import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.GoalItemAction;

public interface IRepository {
    void LoadDailyLog(String sStringID, RepositoryCallback callback);
    void LoadDailyLogByDate(String sDate, RepositoryCallback callback);
    void LoadAllWorkoutTypes(RepositoryCallback callback);
    void LoadAllGoals(RepositoryCallback callback);
    void LoadGoal(String sIdString, RepositoryCallback callback);
    void LoadGoalItemsByDate(String sDate, RepositoryCallback callback);
    void ModifyGoalItem(GoalItemAction action, RepositoryCallback callback);
    void LoadCollection(String sCollection, RepositoryCallback callback);
    void LoadExercise(String idString, RepositoryCallback callback);
    void LoadWorkoutPlanWithExtras(String idString, RepositoryCallback callback);
    void LoadExerciseWithMuscles(String idString, RepositoryCallback callback);
    void LoadWorkoutPlan(String idString, RepositoryCallback callback);
    void LoadWorkoutPlansByExercise(String idString, RepositoryCallback callback);
    void GetWorkoutPlanHistory(String idString, RepositoryCallback callback);
    void LoadAllDailyLogs(RepositoryCallback callback);
    void LoadCurrentGoal(RepositoryCallback callback);
}
