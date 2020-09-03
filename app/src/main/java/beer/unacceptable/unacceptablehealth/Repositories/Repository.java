package beer.unacceptable.unacceptablehealth.Repositories;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.io.Serializable;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.GoalItemAction;

public class Repository implements IRepository, Serializable {
    public void LoadAllDailyLogs(RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog/", null, callback, true);
    }

    public void LoadDailyLog(String sStringID, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog/" + sStringID, null, callback, true);
    }

    /**
     * Loads a daily log by date
     * @param sDate The date to load. Must be in the format "mm/dd/yyyy". It doesn't NEED to be mm, it could be m, it looks it up by integer values
     * @param callback
     */
    public void LoadDailyLogByDate(String sDate, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog/" + sDate, null, callback, true );
    }

    @Override
    public void LoadAllWorkoutTypes(RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/workouttype/", null, callback, true);
    }

    @Override
    public void LoadAllGoals(RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/goal/", null, callback, true);
    }

    @Override
    public void LoadGoal(String sIdString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/goal/" + sIdString, null, callback, true);
    }

    @Override
    public void LoadGoalItemsByDate(String sDate, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/goal/" + sDate, null, callback, true);
    }
    @Override
    public void ModifyGoalItem(GoalItemAction action, RepositoryCallback callback) {
        //TODO: Tools function to convert this to bytes???
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(action);
        byte[] data = json.getBytes();

        Network.WebRequest(Request.Method.POST, Preferences.HealthAPIURL() + "/goal/ModifyGoalItem", data, callback, true);
    }

    @Override
    public void LoadCollection(String sCollection, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/" + sCollection + "/", null, callback, true);
    }

    @Override
    public void LoadExercise(String idString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/exercise/" + idString, null, callback, true);
    }
    @Override
    public void LoadWorkoutPlanWithExtras(String idString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/workoutplan/withextras/" + idString, null, callback, true, true);
    }
    @Override
    public void LoadExerciseWithMuscles(String idString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/exercise/withMuscles/" + idString, null, callback, true, true);
    }
    @Override
    public void LoadWorkoutPlan(String idString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/workoutPlan/" + idString, null, callback, true, true);
    }


    @Override
    public void LoadWorkoutPlansByExercise(String idString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/WorkoutPlan/ByExercise/" + idString, null, callback, true, true);
    }

    @Override
    public void GetWorkoutPlanHistory(String idString, RepositoryCallback callback) {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/Workout/gethistory/" + idString, null, callback, true, true);
    }
}
