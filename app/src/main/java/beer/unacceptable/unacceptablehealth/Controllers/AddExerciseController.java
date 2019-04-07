package beer.unacceptable.unacceptablehealth.Controllers;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

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

    public void Save(String idString, String sName, ArrayList<ListableObject> muscles) {
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

        exercise.Save(m_LibraryRepo);
        view.setScreenTitle(createScreenTitle(exercise));
    }

    private String createScreenTitle(Exercise exercise) {
        return "Exercise: " + exercise.name;
    }

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
        if (Tools.IsEmptyString(idString)) {
            view.setScreenTitle("Add Exercise");
        } else {
            m_repo.LoadExercise(idString, new RepositoryCallback() {
                @Override
                public void onSuccess(String t) {
                    Exercise e = Tools.convertJsonResponseToObject(t, Exercise.class);
                    view.setScreenTitle(createScreenTitle(e));
                    view.PopulateScreen(e);
                }

                @Override
                public void onError(VolleyError error) {
                    view.ShowToast(Tools.ParseVolleyError(error));
                }
            });
        }
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
