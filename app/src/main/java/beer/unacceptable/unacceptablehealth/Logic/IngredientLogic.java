package beer.unacceptable.unacceptablehealth.Logic;

import android.content.Context;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;

import beer.unacceptable.unacceptablehealth.Models.Ingredient;

public class IngredientLogic {
    private ILibraryRepository m_oLibraryRepo;

    public IngredientLogic(ILibraryRepository libraryRepository) {
        m_oLibraryRepo = libraryRepository;
    }

    public boolean save(Context c, NewAdapter adapter, ListableObject i, String sName) {
        if (!allDataEntered(sName)) {
            adapter.InfoMissing(c);
            return false;
        }

        Ingredient ingredient = (Ingredient)i;
        if (ingredient == null)
            ingredient = new Ingredient(sName);

        ingredient.name = sName;

        ingredient.Save(m_oLibraryRepo);

        //add the ingredient if needed
        if (i == null) {
            adapter.add(ingredient);
        }

        return true;
    }

    private boolean allDataEntered(String sName) {
        return sName.length() > 0;
    }
}
