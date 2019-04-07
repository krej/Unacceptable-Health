package beer.unacceptable.unacceptablehealth.Controllers;

import android.content.Context;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Logic.BaseLogic;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class SingleItemListController extends BaseLogic<SingleItemListController.View> {

    private IRepository m_repository;
    private ILibraryRepository m_LibraryRepository;
    private String m_sCollectionName;

    public SingleItemListController(IRepository repository, ILibraryRepository libraryRepository, String sCollectionName) {
        m_repository = repository;
        m_LibraryRepository = libraryRepository;
        m_sCollectionName = sCollectionName;
    }

    public void LoadCollection(String sCollection, final boolean bRefreshing) {
        m_repository.LoadCollection(sCollection, new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                ListableObject[] results = Tools.convertJsonResponseToObject(t, ListableObject[].class);
                view.PopulateList(results, bRefreshing);
            }

            @Override
            public void onError(VolleyError error) {
                String sMessage = Tools.ParseVolleyError(error);
                view.ShowToast(sMessage);
            }
        });
    }


    /*public void LoadAllWorkoutTypes() {
        m_repository.LoadAllWorkoutTypes(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                WorkoutType[] workoutTypes = Tools.convertJsonResponseToObject(t, WorkoutType[].class);
                view.PopulateList(workoutTypes);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }*/

    public boolean save(Context context, NewAdapter m_adapter, ListableObject i, String sName) {
        if (!allInfoEntered(sName)) {
            m_adapter.InfoMissing(context);
            return false;
        }

        boolean bNew = i == null;


        //WorkoutType workoutType = (WorkoutType)i;
        if (i == null) {
            i = new ListableObject();
        }

        i.name = sName;

        i.Save(m_LibraryRepository, m_sCollectionName);

        if (bNew)
            m_adapter.add(i);


        return true;
    }

    private boolean allInfoEntered(String sName) {
        return sName.length() > 0;
    }

    public interface View {
        //void PopulateList(WorkoutType[] workoutTypes);
        void PopulateList(ListableObject[] objects, boolean bRefreshing);
        void ShowToast(String sMessage);
    }
}
