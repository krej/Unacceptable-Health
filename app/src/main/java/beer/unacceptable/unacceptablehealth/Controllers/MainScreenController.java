package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.Response;
import com.unacceptable.unacceptablelibrary.Repositories.ITimeSource;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.CustomReturns.GoalItemsWithWorkoutPlans;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.GoalItemAction;
import beer.unacceptable.unacceptablehealth.Models.Workout;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class MainScreenController extends BaseLogic<MainScreenController.View> {

    private IRepository m_repo;
    private IDateLogic m_date;

    public MainScreenController(IRepository repository, IDateLogic dateLogic) {
        m_repo = repository;
        m_date = dateLogic;
    }

    public void LoadTodaysLog() {
        String sToday = todaysDate();

        m_repo.LoadDailyLogByDate(sToday, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                if (t.contains("Log does not exist.")) {
                    view.showNewLogButton(true);
                    view.showTodaysLog(false);
                } else {
                    DailyLog dl = Tools.convertJsonResponseToObject(t, DailyLog.class);
                    view.showTodaysLog(true);
                    view.showNewLogButton(false);
                    view.populateTodaysLog(dl);
                }
                /*try {
                    DailyLog dl = Tools.convertJsonResponseToObject(t, DailyLog.class);
                } catch (Exception ex) {
                    Response response = Tools.convertJsonResponseToObject(t, Response.class);
                    if (response.Success && response.Message.equals("Log does not exist.")) {
                        view.showNewLogButton(true);
                        view.showTodaysLog(false);
                    }
                }*/
            }

            @Override
            public void onError(VolleyError error) {
                if (Tools.isHttpCleartextError(error)) {
                    view.showToast("Add the APIs new IP address to the apps HTTP Cleartext allowed IPs.");
                }
                view.showTodaysLog(false);
                view.showNewLogButton(false);
                view.showDailyLogError();
            }
        });
    }

    private String todaysDate() {
        Date dtToday = m_date.getTodaysDate();
        String sToday = Tools.FormatDate(dtToday, DailyLog.m_sDateFormat);
        return sToday;
    }

    public void LoadTodaysGoalItems() {
        m_repo.LoadGoalItemsByDate(todaysDate(), new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                GoalItemsWithWorkoutPlans result = Tools.convertJsonResponseToObject(t, GoalItemsWithWorkoutPlans.class, true);
                GoalItem[] goalItems = result.GoalItems;
                WorkoutPlan[] oWorkoutPlans = result.WorkoutPlans;
                boolean bGoalItemsExist = goalItems != null && goalItems.length > 0;

                Arrays.sort(oWorkoutPlans, Comparator.comparing(WorkoutPlan::GetLastUsedAsInt));

                /*if (goalItems.length > 0) {
                    view.populateTodaysGoalItems(goalItems, oWorkoutPlans);
                    view.setGoalItemsVisibility(true);
                    view.setNoGoalLabelVisibility(false);
                } else {
                    view.setGoalItemsVisibility(false);
                    view.setNoGoalLabelVisibility(true);
                }*/

                if (bGoalItemsExist) view.populateTodaysGoalItems(goalItems, oWorkoutPlans);
                view.setGoalItemsVisibility(bGoalItemsExist);
                view.setNoGoalLabelVisibility(!bGoalItemsExist);
                view.enableCompleteWorkoutButton(true);
            }

            @Override
            public void onError(VolleyError error) {
                if (Tools.isHttpCleartextError(error)) {
                    view.showToast("Add the APIs new IP address to the apps HTTP Cleartext allowed IPs.");
                }
            }
        });
    }



    private WorkoutPlan[] sortWorkoutPlans(WorkoutPlan[] plans) {
        Arrays.sort(plans, Comparator.comparing(WorkoutPlan::GetLastUsedAsInt));
        return plans;
    }

    public void ToggleGoalItemComplete(final GoalItem goalItem, final NewAdapter adapter) {
        final GoalItemAction action = new GoalItemAction();
        action.Item = goalItem;
        action.Completed = !goalItem.Completed;
        action.Date = goalItem.Date;
        action.Remove = false;

        m_repo.ModifyGoalItem(action, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Response r = Tools.convertJsonResponseToObject(t, Response.class);
                if (r.Success) {
                    //TODO: Update the screen somehow
                    goalItem.Completed = action.Completed;
                    if (adapter != null) adapter.notifyDataSetChanged();
                } else {
                     view.showToast(r.Message);
                }

            }

            @Override
            public void onError(VolleyError error) {
                Response response = Tools.convertJsonResponseToObject(error.getMessage(), Response.class);

                view.showToast(Tools.ParseVolleyError(error));
            }
        });
    }

    public void SetGoalItemDate(Date newDate, final GoalItem goalItem, final NewAdapter adapter, final boolean bShowAllGoalItems) {
        //newDate = Tools.setTimeToMidnight(newDate);
        final GoalItemAction action = new GoalItemAction();
        action.Item = goalItem;
        action.Completed = goalItem.Completed;
        action.Date = newDate;
        action.Remove = false;

        m_repo.ModifyGoalItem(action, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                goalItem.Date = action.Date;
                if (!bShowAllGoalItems && !Tools.CompareDatesWithoutTime(goalItem.Date, m_date.getTodaysDate())) {
                    adapter.remove(goalItem);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void DeleteGoalItem(final GoalItem goalItem, final NewAdapter adapter) {
        GoalItemAction action = new GoalItemAction();
        action.Item = goalItem;
        action.Remove = true;

        m_repo.ModifyGoalItem(action, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                adapter.remove(goalItem);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public GoalItem CreateNewGoalItem(Workout workout) {
        GoalItem goalItem = new GoalItem(m_date.getTodaysDate(), workout.WorkoutPlan.WorkoutType);
        //goalItem.WorkoutType = workout.WorkoutPlan.WorkoutType;
        goalItem.Completed = true;
        //goalItem.Date = m_date.getTodaysDate();
        //goalItem.name = workout.WorkoutPlan.WorkoutType.name + " on " + m_date.getTodaysDate();

        return goalItem;
    }

    public void LoadCurrentGoal() {
        m_repo.LoadCurrentGoal(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Goal goal = Tools.convertJsonResponseToObject(t, Goal.class);
                view.SetGoal(goal);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public interface View {

        void showTodaysLog(boolean b);

        void showNewLogButton(boolean b);

        void populateTodaysLog(DailyLog dl);
        void showDailyLogError();
        void populateTodaysGoalItems(GoalItem[] goalItems, WorkoutPlan[] plans);
        void setGoalItemsVisibility(boolean bVisible);
        void setNoGoalLabelVisibility(boolean bVisible);
        void showToast(String sMessage);
        void enableCompleteWorkoutButton(boolean bEnabled);
        void SetGoal(Goal goal);
    }
}
