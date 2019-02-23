package beer.unacceptable.unacceptablehealth.Controllers;

import android.content.Context;
import android.text.Editable;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class WorkoutTypeController extends BaseLogic<WorkoutTypeController.View> {

    private IRepository m_repository;
    private ILibraryRepository m_LibraryRepository;

    public WorkoutTypeController(IRepository repository, ILibraryRepository libraryRepository) {
        m_repository = repository;
        m_LibraryRepository = libraryRepository;
    }

    public void LoadAllWorkoutTypes() {
        m_repository.LoadAllWorkoutTypes(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutType[] workoutTypes = Tools.convertJsonResponseToObject(t, WorkoutType[].class);
                view.PopulateWorkoutTypes(workoutTypes);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public boolean save(Context context, NewAdapter m_adapter, ListableObject i, String sName) {
        if (!allInfoEntered(sName)) {
            m_adapter.InfoMissing(context);
            return false;
        }

        WorkoutType workoutType = (WorkoutType)i;
        if (workoutType == null)
            workoutType = new WorkoutType();

        workoutType.name = sName;

        workoutType.Save(m_LibraryRepository);

        if (i == null)
            m_adapter.add(workoutType);


        return true;
    }

    private boolean allInfoEntered(String sName) {
        return sName.length() > 0;
    }

    public interface View {
        void PopulateWorkoutTypes(WorkoutType[] workoutTypes);
    }
}
