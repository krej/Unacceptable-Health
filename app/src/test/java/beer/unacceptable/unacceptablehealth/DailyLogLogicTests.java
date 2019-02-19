package beer.unacceptable.unacceptablehealth;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Logic.DailyLogLogic;
import beer.unacceptable.unacceptablehealth.Logic.IDateLogic;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DailyLogLogicTests {
    private DailyLogLogic m_oLogic;
    private IDateLogic m_date;
    private IRepository m_repository;
    private ILibraryRepository m_libraryRepository;
    private DailyLogLogic.View view;

    @Before
    public void setup() {
        m_date = mock(IDateLogic.class);
        m_repository = mock(IRepository.class);
        m_libraryRepository = mock(ILibraryRepository.class);
        view = mock(DailyLogLogic.View.class);

        m_oLogic = new DailyLogLogic(m_repository, m_date, m_libraryRepository);
        m_oLogic.attachView(view);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return getHardCodedDate();
            }
        }).when(m_date).getTodaysDate();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                //this is copied from viewing a DailyLog document in Robo3T
                callback.onSuccess("{\n" +
                        "    \"Id\": \"5c63a0cd32856d03e41570df\",\n" +
                        "    \"idString\": \"5c63a0cd32856d03e41570df\",\n" +
                        "    \"name\": \"Empty\",\n" +
                        "    \"date\": \"2019-02-12T12:00:00Z\",\n" +
                        "    \"HealthRating\": 5,\n" +
                        "    \"BBD\": false,\n" +
                        "    \"UsedFlonase\": false,\n" +
                        "    \"FlonaseReasoning\": 0,\n" +
                        "    \"HadHeadache\": true,\n" +
                        "    \"WorkDay\": false,\n" +
                        "    \"WorkRating\": 0,\n" +
                        "    \"PersonalDayRating\": 5,\n" +
                        "    \"MindfulMoment\": \"111\",\n" +
                        "    \"OverallNotes\": \"333\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLog(eq("myID"), any(RepositoryCallback.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                //this is copied from viewing a DailyLog document in Robo3T
                callback.onSuccess("{\n" +
                        "    \"Id\": \"5c63a0cd32856d03e41570df\",\n" +
                        "    \"idString\": \"5c63a0cd32856d03e41570df\",\n" +
                        "    \"name\": \"Empty\",\n" +
                        "    \"date\": \"2018-01-25T12:00:00Z\",\n" +
                        "    \"HealthRating\": 5,\n" +
                        "    \"BBD\": false,\n" +
                        "    \"UsedFlonase\": false,\n" +
                        "    \"FlonaseReasoning\": 0,\n" +
                        "    \"HadHeadache\": true,\n" +
                        "    \"WorkDay\": false,\n" +
                        "    \"WorkRating\": 0,\n" +
                        "    \"PersonalDayRating\": 5,\n" +
                        "    \"MindfulMoment\": \"111\",\n" +
                        "    \"OverallNotes\": \"333\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLog(eq("olderLog"), any(RepositoryCallback.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onError(new VolleyError());
                return null;
            }
        }).when(m_repository).LoadDailyLog(eq("bad response"), any(RepositoryCallback.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onError(null);
                return null;
            }
        }).when(m_repository).LoadDailyLog(eq("null response"), any(RepositoryCallback.class));
    }

    private Date getHardCodedDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, Calendar.FEBRUARY); //changed to Calendar.FEBRUARY because its off by one
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.HOUR_OF_DAY, 7); //hard code an actual time because thats what new Date() returns when you run the program
        calendar.set(Calendar.MINUTE, 36);
        calendar.set(Calendar.SECOND, 24);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dt = calendar.getTime();
        return dt;
    }

    @Test
    public void loadEmptyID_CreateNewLog() {
        m_oLogic.LoadLog("");

        Date actual = m_oLogic.getLog().date;
        Assert.assertEquals(getHardCodedDate(), actual);
        verify(view).setScreenControlsEnabled(true);
        verify(m_repository, never()).LoadDailyLog(anyString(), any(RepositoryCallback.class));
    }

    //I'm not sure if this would ever have a null id, but thats what tests are for, to make sure it will in case it does
    @Test
    public void loadNullId_CreateNewLog() {
        m_oLogic.LoadLog(null);

        Date actual = m_oLogic.getLog().date;
        Assert.assertEquals(getHardCodedDate(), actual);
        verify(view).setScreenControlsEnabled(true);
        verify(m_repository, never()).LoadDailyLog(anyString(), any(RepositoryCallback.class));
    }

    @Test
    public void loadWithID_fromSameDay_LogLoaded() {
        m_oLogic.LoadLog("myID");

        verify(m_repository).LoadDailyLog(eq("myID"), any(RepositoryCallback.class));
        verify(view).setScreenControlsEnabled(true);
        verify(view).setScreenTitle(anyString());
        verify(view).fillScreen(m_oLogic.getLog());
    }

    @Test
    public void loadWithID_fromPreviousDay_LogLoadedReadOnly() {
        m_oLogic.LoadLog("olderLog");

        verify(m_repository).LoadDailyLog(eq("olderLog"), any(RepositoryCallback.class));
        verify(view).setScreenControlsEnabled(false);
        verify(view).setScreenTitle(anyString());
        verify(view).fillScreen(m_oLogic.getLog());
    }

    @Test
    public void loadWithID_getBadReponse_lockDownScreen() {
        m_oLogic.LoadLog("bad response");

        verify(m_repository).LoadDailyLog(eq("bad response"), any(RepositoryCallback.class));
        verify(view).setScreenControlsEnabled(false);
        verify(view).showMessage(anyString());
    }

    @Test
    public void loadWithID_getNullReponse_lockDownScreen() {
        m_oLogic.LoadLog("null response");

        verify(m_repository).LoadDailyLog(eq("null response"), any(RepositoryCallback.class));
        verify(view).setScreenControlsEnabled(false);
        verify(view).showMessage(anyString());
    }

    @Test
    public void attemptToSaveOnOldLog_saveDoesntHappen() {
        m_oLogic.LoadLog("olderLog");
        m_oLogic.saveLog("1/25/2018", 1, true, true, true, true, true, true, true, 1, 1, "", "");

        verify(view).showMessage(anyString());
        verify(m_libraryRepository, never()).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
    }

    @Test
    public void attemptToSaveCurrentLog_saveHappens() {
        m_oLogic.LoadLog("myID");

        m_oLogic.saveLog("2/12/2019", 1, true, true, true, true, true, true, true, 1, 1, "", "");

        //verify(m_libraryRepository).Save(anyString(), eq(m_oLogic.getLog().BuildRestData()), any(RepositoryCallback.class));
        verify(m_libraryRepository).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
    }

    @Test
    public void saveWithFlonaseBadAllergy_flonaseReasoningBecomes1() {
        m_oLogic.LoadLog("myID");

        m_oLogic.saveLog("2/12/2019", 1, true, true, true, false, false, true, true, 1, 1, "", "");

        Assert.assertEquals(1, m_oLogic.getLog().FlonaseReasoning);
    }

    @Test
    public void saveWithoutUsedFlonaseButFlonaseReasoning_flonaseReasoningBecomes0() {
        m_oLogic.LoadLog("myID");

        m_oLogic.saveLog("2/12/2019", 1, true, false, true, true, true, true, true, 1, 1, "", "");

        Assert.assertEquals(0, m_oLogic.getLog().FlonaseReasoning);
    }

    @Test
    public void saveWithoutWorkDay_workDayRatingBecomes0() {
        m_oLogic.LoadLog("myID");

        m_oLogic.saveLog("2/12/2019", 1, true, true, true, true, true, true, false, 5, 1, "", "");

        Assert.assertEquals(0, m_oLogic.getLog().WorkRating);
    }

    @Test
    public void saveWithWorkDay_workDayRatingStaysTheSame() {
        m_oLogic.LoadLog("myID");

        m_oLogic.saveLog("2/12/2019", 1, true, true, true, true, true, true, true, 5, 1, "", "");

        Assert.assertEquals(5, m_oLogic.getLog().WorkRating);
    }

    //tests for the data going into the DailyLogList items
    @Test
    public void calculateOverallDayRating_WorkAndPersonal() {
        DailyLog log = new DailyLog();
        log.WorkDay = true;
        log.WorkRating = 2;
        log.PersonalDayRating = 5;

        double rating;
        rating = m_oLogic.getDaysAverageRating(log);

        Assert.assertEquals(3.5, rating, 0.001);

        log.WorkRating = 1;
        log.PersonalDayRating = 2;

        rating = m_oLogic.getDaysAverageRating(log);

        Assert.assertEquals(1.5, rating, 0.001);
    }

    @Test
    public void calculateOverallDayRating_NoWorkDay() {
        DailyLog log = new DailyLog();
        log.WorkDay = false;
        log.WorkRating = 0;
        log.PersonalDayRating = 5;

        double rating;
        rating = m_oLogic.getDaysAverageRating(log);
        Assert.assertEquals(5, rating, 0.1);

        log.PersonalDayRating = 2;
        rating = m_oLogic.getDaysAverageRating(log);
        Assert.assertEquals(2, rating, 0.1);
    }
}
