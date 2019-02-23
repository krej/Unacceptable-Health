package beer.unacceptable.unacceptablehealth;

import android.app.Dialog;
import android.content.Context;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import beer.unacceptable.unacceptablehealth.Controllers.WorkoutTypeController;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class WorkoutTypeControllerTests {
    private WorkoutTypeController m_oController;
    private IRepository m_repo;
    private ILibraryRepository m_libraryRepo;
    private WorkoutTypeController.View m_view;

    @Before
    public void setup() {
        m_repo = mock(IRepository.class);
        m_libraryRepo = mock(ILibraryRepository.class);
        m_view = mock(WorkoutTypeController.View.class);
        m_oController = new WorkoutTypeController(m_repo, m_libraryRepo);
        m_oController.attachView(m_view);
    }

    @Test
    public void loadAllWorkoutTypes_ReturnsOneWorkoutType() {
        ArgumentCaptor<WorkoutType[]> captor = ArgumentCaptor.forClass(WorkoutType[].class);

        WorkoutType[] expectedResults = new WorkoutType[1];
        expectedResults[0] = new WorkoutType();
        expectedResults[0].name = "Run";

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(0);
                //this is copied from viewing a DailyLog document in Robo3T
                callback.onSuccess("[{\"name\": \"Run\"}]");
                return null;
            }
        }).when(m_repo).LoadAllWorkoutTypes(any(RepositoryCallback.class));

        m_oController.LoadAllWorkoutTypes();
        verify(m_repo).LoadAllWorkoutTypes(any(RepositoryCallback.class));
        verify(m_view).PopulateWorkoutTypes(captor.capture());
        Assert.assertTrue(captor.getValue().length == 1);
        Assert.assertTrue(captor.getValue()[0].name.equals("Run"));
    }

    @Test
    public void saveItem_NoName_DoesntSave() {
        boolean result = m_oController.save(mock(Context.class), mock(NewAdapter.class), null, "");

        verify(m_libraryRepo, never()).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
        Assert.assertFalse(result);
    }

    @Test
    public void saveItem_WithName_Saves() {

        boolean result = m_oController.save(mock(Context.class), mock(NewAdapter.class), null, "");

        verify(m_libraryRepo).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
        Assert.assertTrue(result);
    }
}
