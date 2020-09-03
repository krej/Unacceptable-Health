package beer.unacceptable.unacceptablehealth;

import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.ITimeSource;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import beer.unacceptable.unacceptablehealth.Controllers.PerformWorkoutController;
import beer.unacceptable.unacceptablehealth.Models.WorkoutPlan;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class PerformWorkoutTests {

    PerformWorkoutController m_oController;
    PerformWorkoutController.View view;
    ILibraryRepository libraryRepository;
    IRepository repository;
    WorkoutPlan m_WorkoutPlan;
    String m_sTestPlan;
    ITimeSource m_TimeSource;

    @Before
    public void setup() {
        view = mock(PerformWorkoutController.View.class);
        libraryRepository = mock(ILibraryRepository.class);
        repository = mock(IRepository.class);
        m_TimeSource = mock(ITimeSource.class);
        m_oController = new PerformWorkoutController(repository, libraryRepository, m_TimeSource);
        m_oController.attachView(view);

        m_sTestPlan = "{\"idString\":\"5cae783ac24c7f33b82a94f1\",\"name\":\"Test Workout 1\",\"WorkoutType\":{\"idString\":\"5c6f8938f293eb1db4da1e62\",\"name\":\"Arms\"},\"ExercisePlans\":[{\"idString\":null,\"name\":\"Bicep Curl\",\"Exercise\":{\"idString\":\"5caa487a60c3331f2cfffcbd\",\"name\":\"Bicep Curl\",\"Muscles\":[{\"idString\":\"5ca692cf81005e41b045712f\",\"name\":\"Bicep\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true},\"Order\":0,\"Reps\":6,\"Sets\":2,\"Weight\":3.0,\"Seconds\":0},{\"idString\":null,\"name\":\"Tricep Dips\",\"Exercise\":{\"idString\":\"5caa95026063b146b8ab550a\",\"name\":\"Tricep Dips\",\"Muscles\":[{\"idString\":\"5ca6c912f8d26a41943b9186\",\"name\":\"Tricep\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":false},\"Order\":0,\"Reps\":5,\"Sets\":6,\"Weight\":7.0,\"Seconds\":8},{\"idString\":null,\"name\":\"Plank\",\"Exercise\":{\"idString\":\"5cae9cc627ba9d2ab8569e01\",\"name\":\"Plank\",\"Muscles\":[{\"idString\":\"5cae7993089abb0de8c5f9b2\",\"name\":\"Abs\"}],\"ShowWeight\":false,\"ShowTime\":true,\"ShowReps\":false},\"Order\":0,\"Reps\":0,\"Sets\":5,\"Weight\":0.0,\"Seconds\":10}],\"CalorieLogs\":null,\"TotalCalories\":0,\"Id\":\"5cae783ac24c7f33b82a94f1\"}";

        m_WorkoutPlan = Tools.convertJsonResponseToObject(m_sTestPlan,
                WorkoutPlan.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess(m_sTestPlan);
                return null;
            }
        }).when(repository).LoadWorkoutPlan(eq("5cae783ac24c7f33b82a94f1"), any(RepositoryCallback.class));

    }

    @Test
    public void loadAndCompleteFirstExercise() {
        m_oController.LoadWorkoutPlan("5cae783ac24c7f33b82a94f1");

        verify(view).PopulateScreenWithExercisePlan(eq(m_WorkoutPlan.ExercisePlans.get((0))));
        verify(view, never()).CompleteWorkout(null);
        clearInvocations(view);

        m_oController.finishSet();

        verify(view).SwitchToRestView();
        verify(view).StartRestChronometer(any(long.class));
        verify(view, never()).CompleteWorkout(null);
        clearInvocations(view);

        m_oController.finishRest(null);

        verify(view).SwitchToWorkoutView();
        verify(view).PopulateScreenWithExercisePlan(m_WorkoutPlan.ExercisePlans.get(0));
        verify(view).StopRestChronometer();
        verify(view, never()).CompleteWorkout(null);
        clearInvocations(view);

        m_oController.finishSet();

        verify(view).SwitchToRestView();
        verify(view).StartRestChronometer(any(long.class));
        verify(view, never()).CompleteWorkout(null);
        clearInvocations(view);

        m_oController.finishRest(null);

        verify(view).SwitchToWorkoutView();
        verify(view).PopulateScreenWithExercisePlan(m_WorkoutPlan.ExercisePlans.get(1));
        verify(view).StopRestChronometer();
        verify(view, never()).CompleteWorkout(null);
        clearInvocations(view);
    }

    @Test
    public void finalExerciseComplete_finishSetPushed_GoToCompleteScreen() {
        m_oController.LoadWorkoutPlan("5cae783ac24c7f33b82a94f1");

        //finish all the sets we have
        for (int i = 0; i < 2+6+5; i++ ) {
            m_oController.finishSet();
            m_oController.finishRest(null);
        }

        verify(view).CompleteWorkout(null);
    }
}
