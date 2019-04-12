package beer.unacceptable.unacceptablehealth.Controllers;

import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Models.CustomReturns.ExerciseWithMuscleList;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.Muscle;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class AddExerciseController extends BaseLogic<AddExerciseController.View> {

    private IRepository m_repo;
    private ILibraryRepository m_LibraryRepo;

    public AddExerciseController(IRepository repo, ILibraryRepository libraryRepository) {
        m_repo = repo;
        m_LibraryRepo = libraryRepository;
    }

    public void LoadMuscles() {
        m_repo.LoadCollection("muscle", new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Muscle[] muscles = Tools.convertJsonResponseToObject(t, Muscle[].class);
                view.PopulateMuscleList(muscles);
            }

            @Override
            public void onError(VolleyError error) {
                String sMessage = Tools.ParseVolleyError(error);
                view.ShowToast(sMessage);
            }
        });
    }

    public void Save(String idString, String sName, ArrayList<ListableObject> muscles, boolean bShowWeight, boolean bShowTime, boolean bShowReps) {
        boolean bQuit = false;
        view.ClearErrors();

        if (Tools.IsEmptyString(sName)) {
            view.SetNameError("Name is Required");
            bQuit = true;
        }

        if (muscles == null || muscles.size() == 0) {
            view.ShowToast("At least one muscle is required.");
            bQuit = true;
        }

        if (bQuit) return;

        Exercise exercise = new Exercise();
        exercise.idString = idString;
        exercise.name = sName;
        exercise.Muscles = convertToMuscleArrayList(muscles);
        exercise.ShowTime = bShowTime;
        exercise.ShowWeight = bShowWeight;
        exercise.ShowReps = bShowReps;

        exercise.Save(m_LibraryRepo);
        view.setScreenTitle(createScreenTitle(exercise));
    }

    private String createScreenTitle(Exercise exercise) {
        if (exercise == null) return "Add Exercise";
        return "Exercise: " + exercise.name;
    }

    //TODO: Convert to Tools.ConvertToStrongTypedList
    private ArrayList<Muscle> convertToMuscleArrayList(ArrayList<ListableObject> muscles) {
        ArrayList<Muscle> muscleArrayList = new ArrayList<>();
        for (ListableObject listableObject : muscles) {
            Muscle m = new Muscle();
            m.name = listableObject.name;
            m.idString = listableObject.idString;
            muscleArrayList.add(m);
        }

        return muscleArrayList;
    }

    public void LoadExercise(String idString) {
        if (idString == null) idString = "";

        m_repo.LoadExerciseWithMuscles(idString, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                ExerciseWithMuscleList oResult = Tools.convertJsonResponseToObject(t, ExerciseWithMuscleList.class);
                Exercise e = oResult.Exercise;
                Muscle[] muscles = oResult.Muscles;

                view.setScreenTitle(createScreenTitle(e));
                view.PopulateMuscleList(muscles);
                if (e != null) view.PopulateScreen(e);
            }

            @Override
            public void onError(VolleyError error) {
                view.ShowToast(Tools.ParseVolleyError(error));
            }
        });
    }

    public boolean AddMuscleButtonEnabled(Muscle[] muscles) {
        return muscles.length > 0;
    }

    public static int getVisibility(boolean bVisible) {
        if (bVisible)
            return android.view.View.VISIBLE;
        return android.view.View.GONE;
    }

    public void ClearValueBasedOnVisibility(EditText editText, boolean bVisible) {
        if (!bVisible)
            Tools.SetText(editText, "");
    }

    public interface View {
        void ShowToast(String sMessage);
        void PopulateMuscleList(Muscle[] muscles);
        void SetNameError(String sError);
        void ClearErrors();
        void setScreenTitle(String sTitle);
        void PopulateScreen(Exercise exercise);
    }
}
