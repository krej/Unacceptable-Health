package beer.unacceptable.unacceptablehealth;

import android.content.Context;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import beer.unacceptable.unacceptablehealth.Controllers.IngredientLogic;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class IngredientLogicTests {
    private IngredientLogic m_oLogic;
    private NewAdapter m_Adapter;
    private LibraryRepository m_repo;
    private Context m_Context;
    private boolean bSaveSuccess;

    @Before
    public void setUp() {
        m_Adapter = mock(NewAdapter.class);
        m_repo = mock(LibraryRepository.class);
        m_oLogic = new IngredientLogic(m_repo);
        m_Context = mock(Context.class);
    }

    @Test
    public void saveWithNoName_ErrorPopsUp() {

        bSaveSuccess = m_oLogic.save(m_Context, m_Adapter, new ListableObject(), "");

        verify(m_Adapter).InfoMissing(m_Context);
        Assert.assertFalse(bSaveSuccess);
    }

    @Test
    public void saveWithName_SaveHappens() {
        Ingredient i = new Ingredient("Test");
        bSaveSuccess = m_oLogic.save(m_Context, m_Adapter, i, "Test");

        verify(m_Adapter, never()).InfoMissing(m_Context);
        verify(m_repo).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
        verify(m_Adapter, never()).add(i);
        Assert.assertTrue(bSaveSuccess);
    }

    @Test
    public void saveNewItemWithName_SaveHappens() {
        bSaveSuccess = m_oLogic.save(m_Context,m_Adapter, null, "Test");

        verify(m_Adapter, never()).InfoMissing(m_Context);
        verify(m_repo).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
        verify(m_Adapter).add(any(ListableObject.class));
        Assert.assertTrue(bSaveSuccess);
    }
}
