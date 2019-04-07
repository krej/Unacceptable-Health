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
    }

    @Test
    public void emptyScreen_SaveClicked_ErrorsShown() {
        m_oController.Save("","", new ArrayList<ListableObject>());

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

        m_oController.Save("123","Test", muscles);

        verify(view, never()).SetNameError(anyString());
        verify(libraryRepository).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
        verify(view).setScreenTitle("Exercise: Test");
    }

    @Test
    public void emptyScreen_ErrorsShow_FixError_ErrorCleared() {
        m_oController.Save("","", null);

        verify(view).SetNameError(anyString());

        m_oController.Save("", "Test", new ArrayList<ListableObject>());

        verify(view, atLeastOnce()).ClearErrors();
    }

    @Test
    public void noExercisePassedIn_TitleSetToAdd_DontPopulateScreen() {
        m_oController.LoadExercise(null);

        verify(view).setScreenTitle(eq("Add Exercise"));
        verify(view, never()).PopulateScreen(any(Exercise.class));
        verify(repo, never()).LoadExercise(anyString(), any(RepositoryCallback.class));
    }

    @Test
    public void exercisePassedIn_TitleSetToEdit_PopulateScreen() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess(
                        "{\n" +
                        "  \"_id\": \"5caa3d16b6ca661f646ee0c2\",\n" +
                        "  \"name\": \"Bicep Curl\",\n" +
                        "  \"Muscles\": [\n" +
                        "    {\n" +
                        "      \"name\": \"Bicep\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
                return null;
            }
        }).when(repo).LoadExercise(eq("123"), any(RepositoryCallback.class));

        m_oController.LoadExercise("123");

        verify(view).setScreenTitle(eq("Exercise: Bicep Curl"));
        verify(view).PopulateScreen(any(Exercise.class));
    }
}
