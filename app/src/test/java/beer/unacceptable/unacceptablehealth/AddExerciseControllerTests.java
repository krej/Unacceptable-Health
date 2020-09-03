package beer.unacceptable.unacceptablehealth;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Controllers.AddExerciseController;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.Muscle;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class AddExerciseControllerTests {
    private IRepository repo;
    private AddExerciseController.View view;
    private AddExerciseController m_oController;
    private ILibraryRepository libraryRepository;

    @Before
    public void setup() {
        repo = mock(IRepository.class);
        view = mock(AddExerciseController.View.class);
        libraryRepository = mock(ILibraryRepository.class);
        m_oController = new AddExerciseController(repo, libraryRepository);
        m_oController.attachView(view);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess(
                        "{\"Exercise\":{\"idString\":\"5caa95026063b146b8ab550a\",\"name\":\"Tricep Dips\",\"Muscles\":[{\"idString\":\"5ca6c912f8d26a41943b9186\",\"name\":\"Tricep\"}],\"Id\":\"5caa95026063b146b8ab550a\"},\"Muscles\":[{\"idString\":\"5ca692cf81005e41b045712f\",\"name\":\"Bicep\",\"Id\":\"5ca692cf81005e41b045712f\"},{\"idString\":\"5ca6c912f8d26a41943b9186\",\"name\":\"Tricep\",\"Id\":\"5ca6c912f8d26a41943b9186\"}]}");
                return null;
            }
        }).when(repo).LoadExerciseWithMuscles(eq("123"), any(RepositoryCallback.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\"Exercise\":null,\"Muscles\":[{\"idString\":\"5ca692cf81005e41b045712f\",\"name\":\"Bicep\",\"Id\":\"5ca692cf81005e41b045712f\"},{\"idString\":\"5ca6c912f8d26a41943b9186\",\"name\":\"Tricep\",\"Id\":\"5ca6c912f8d26a41943b9186\"},{\"idString\":\"5cae7985089abb0de8c5f9b1\",\"name\":\"Chest\",\"Id\":\"5cae7985089abb0de8c5f9b1\"},{\"idString\":\"5cae7993089abb0de8c5f9b2\",\"name\":\"Abs\",\"Id\":\"5cae7993089abb0de8c5f9b2\"}]}");
                return null;
            }
        }).when(repo).LoadExerciseWithMuscles(eq(""), any(RepositoryCallback.class));
    }

    @Test
    public void emptyScreen_SaveClicked_ErrorsShown() {
        m_oController.Save("", new ArrayList<ListableObject>(), false, false, false, "");

        verify(view).SetNameError(anyString());
        verify(view).ShowToast(anyString());
        verify(libraryRepository, never()).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
    }

    @Test
    public void dataEntry_SaveClicked_SaveCalled() {
        ArrayList<ListableObject> muscles = new ArrayList<>();
        Muscle m = new Muscle();
        m.name = "Bicep";
        muscles.add(m);

        m_oController.Save("Test", muscles, false, false, false, "123");

        verify(view, never()).SetNameError(anyString());
        verify(libraryRepository).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
        verify(view).setScreenTitle("Exercise: Test");
    }

    @Test
    public void emptyScreen_ErrorsShow_FixError_ErrorCleared() {
        m_oController.Save("", null, false, false, false, "");

        verify(view).SetNameError(anyString());

        m_oController.Save( "Test", new ArrayList<ListableObject>(), false, false, false, "");

        verify(view, atLeastOnce()).ClearErrors();
    }

    @Test
    public void noExercisePassedIn_TitleSetToAdd_DontPopulateScreen() {
        m_oController.LoadExercise(null);

        verify(view).setScreenTitle(eq("Add Exercise"));
        verify(view, never()).PopulateScreen(any(Exercise.class));
        verify(view, never()).PopulateScreen((Exercise)isNull());
        verify(repo).LoadExerciseWithMuscles(anyString(), any(RepositoryCallback.class));
        verify(view).PopulateMuscleList(any(Muscle[].class));
    }

    @Test
    public void exercisePassedIn_TitleSetToEdit_PopulateScreen() {
        m_oController.LoadExercise("123");

        verify(view).setScreenTitle(eq("Exercise: Tricep Dips"));
        verify(view).PopulateScreen(any(Exercise.class));
        verify(view).PopulateMuscleList(any(Muscle[].class));
    }
}
