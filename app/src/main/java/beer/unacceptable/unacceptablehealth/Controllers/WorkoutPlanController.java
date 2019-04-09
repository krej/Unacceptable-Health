package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Models.CustomReturns.WorkoutPlanWithExtras;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class WorkoutPlanController extends BaseLogic<WorkoutPlanController.View> {

    private IRepository m_repo;
    private ILibraryRepository m_LibraryRepo;
    WorkoutPlan m_WorkoutPlan;

    public WorkoutPlanController(IRepository repo, ILibraryRepository LibraryRepo) {
        m_repo = repo;
        m_LibraryRepo = LibraryRepo;
    }

    public void LoadWorkoutTypes() {
        m_repo.LoadAllWorkoutTypes(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutType[] workoutTypes = Tools.convertJsonResponseToObject(t, WorkoutType[].class);
                view.PopulateWorkoutType(workoutTypes);
            }

            @Override
            public void onError(VolleyError error) {
                view.ShowToast(Tools.ParseVolleyError(error));
            }
        });
    }

    public void LoadExercises() {
        m_repo.LoadCollection("exercise", new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Exercise[] exercises = Tools.convertJsonResponseToObject(t, Exercise[].class);
                view.PopulateExercises(exercises);
            }

            @Override
            public void onError(VolleyError error) {
                view.ShowToast(Tools.ParseVolleyError(error));
            }
        });
    }

    public void Save(String sName, WorkoutType workoutType, ArrayList<ListableObject> exercisePlans) {
        view.ClearErrors();

        boolean bQuit = false;

        if (Tools.IsEmptyString(sName)) {
            view.SetNameError("Name is required");
            bQuit = true;
        }

        if (workoutType == null ) {
            view.ShowToast("Workout Type is required");
            bQuit = true;
        }

        if (exercisePlans == null || exercisePlans.size() == 0) {
            view.ShowToast("Exercises Required");
            bQuit = true;
        }

        if (bQuit) return;

        if (m_WorkoutPlan == null)
            m_WorkoutPlan = new WorkoutPlan();

        m_WorkoutPlan.name = sName;
        //m_WorkoutPlan.idString = idString;
        m_WorkoutPlan.WorkoutType = workoutType;
        m_WorkoutPlan.ExercisePlans = Tools.ConvertToStrongTypedArrayList(exercisePlans);

        m_WorkoutPlan.Save(m_LibraryRepo);
    }

    public void LoadWorkoutPlan(String idString) {
        //if (Tools.IsEmptyString(idString)) return;

        m_repo.LoadWorkoutPlanWithExtras(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutPlanWithExtras data = Tools.convertJsonResponseToObject(t, WorkoutPlanWithExtras.class);

                view.PopulateExercises(data.Exercises);
                view.PopulateWorkoutType(data.WorkoutTypes);
                if (data.WorkoutPlan != null) {
                    view.PopulateWorkoutPlan(data.WorkoutPlan);
                    m_WorkoutPlan = data.WorkoutPlan;
                }
            }

            @Override
            public void onError(VolleyError error) {
                view.ShowToast(Tools.ParseVolleyError(error));
            }
        });
    }

    public int getWorkoutTypeSpinnerIndex(WorkoutType[] workoutTypes, WorkoutType workoutType) {
        for (int i = 0; i < workoutTypes.length; i++) {
            if (workoutTypes[i].idString.equals(workoutType.idString))
                return i;
        }

        return -1;
    }


    public interface View {
        void ShowToast(String sMessage);
        void PopulateWorkoutType(WorkoutType[] workoutTypes);
        void PopulateExercises(Exercise[] exercises);
        void PopulateWorkoutPlan(WorkoutPlan workoutPlan);

        void ClearErrors();
        void SetNameError(String sMessage);
    }
}
