package beer.unacceptable.unacceptablehealth;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Controllers.IDateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class MainScreenControllerTest {
    private MainScreenController m_oController;
    private IRepository m_repo;
    private MainScreenController.View view;
    private IDateLogic m_date;

    @Before
    public void setup() {
        m_repo = mock(IRepository.class);
        view = mock(MainScreenController.View.class);
        m_date = mock(IDateLogic.class);
        m_oController = new MainScreenController(m_repo, m_date);
        m_oController.attachView(view);


        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return getHardCodedDate();
            }
        }).when(m_date).getTodaysDate();
    }

    private Date getHardCodedDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, Calendar.FEBRUARY); //changed to Calendar.FEBRUARY because its off by one
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.HOUR_OF_DAY, 7); //hard code an actual time because thats what new Date() returns when you run the program
        calendar.set(Calendar.MINUTE, 36);
        calendar.set(Calendar.SECOND, 24);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dt = calendar.getTime();
        return dt;
    }

    @Test
    public void screenLoads_NoDailyLogStarted_NewLogButtonShows() {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\n" +
                        "    \"Success\": true,\n" +
                        "    \"Message\": \"Log does not exist.\"\n" +
                        "}");
                return null;
            }
        }).when(m_repo).LoadDailyLogByDate(eq("02/22/2019"), any(RepositoryCallback.class));

        m_oController.LoadTodaysLog();

        verify(m_repo).LoadDailyLogByDate(eq("02/22/2019"), any(RepositoryCallback.class));
        verify(view).showTodaysLog(false);
        verify(view).showNewLogButton(true);
        verify(view, never()).populateTodaysLog(any(DailyLog.class));
    }

    @Test
    public void screenLoads_DailyLogStarted_ShowAndPopulateLogView() {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\n" +
                        "    \"Id\": \"5c6380fe32856d03e41570db\",\n" +
                        "    \"idString\": \"5c6380fe32856d03e41570db\",\n" +
                        "    \"name\": \"02/12/2019\",\n" +
                        "    \"date\": \"2019-02-12T12:00:00Z\",\n" +
                        "    \"HealthRating\": 0,\n" +
                        "    \"BBD\": false,\n" +
                        "    \"UsedFlonase\": false,\n" +
                        "    \"FlonaseReasoning\": 0,\n" +
                        "    \"HadHeadache\": false,\n" +
                        "    \"WorkDay\": false,\n" +
                        "    \"WorkRating\": 0,\n" +
                        "    \"PersonalDayRating\": 0,\n" +
                        "    \"MindfulMoment\": \"isn't it an issue with apostle?\",\n" +
                        "    \"OverallNotes\": \"\"\n" +
                        "}");
                return null;
            }
        }).when(m_repo).LoadDailyLogByDate(eq("02/22/2019"), any(RepositoryCallback.class));

        m_oController.LoadTodaysLog();

        verify(m_repo).LoadDailyLogByDate(eq("02/22/2019"), any(RepositoryCallback.class));
        verify(view).showTodaysLog(true);
        verify(view).showNewLogButton(false);
        verify(view).populateTodaysLog(any(DailyLog.class));
    }

    @Test
    public void screenLods_FailureLoadingDailyLog_ShowError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onError(new VolleyError());
                return null;
            }
        }).when(m_repo).LoadDailyLogByDate(anyString(), any(RepositoryCallback.class));

        m_oController.LoadTodaysLog();

        verify(view).showNewLogButton(false);
        verify(view).showTodaysLog(false);
        verify(view).showDailyLogError();
    }

    @Test
    public void screenLoads_LoadTodaysGoalItems_SuccessPopulatesScreen() {

    }
}
