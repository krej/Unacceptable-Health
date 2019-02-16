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
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class DailyLogLogicNotificationTests {
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
    public void loadTodaysLog_doesntExist_ReturnDoesntExist() {
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
        }).when(m_repository).LoadDailyLogByDate(eq("02/12/2019"), any(RepositoryCallback.class));
        setUpFailResponseForNotificationTests();

        m_oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Assert.assertTrue("DailyLogLogic said not to continue when it should.", m_oLogic.continueToLog());
                Assert.assertNull("New DailyLog ID was not null.", m_oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {
                Assert.fail("Repository returned error when shouldn't have.");
            }
        });
    }

    @Test
    public void loadTodaysLog_existsButNotFinished_ReturnsExistsAndOpensLog() {
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
                        "    \"MindfulMoment\": \"\",\n" +
                        "    \"OverallNotes\": \"\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLogByDate(eq("02/12/2019"), any(RepositoryCallback.class));

        setUpFailResponseForNotificationTests();

        m_oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Assert.assertTrue("DailyLogLogic said not to continue when it should.", m_oLogic.continueToLog());
                Assert.assertEquals("Loaded DailyLogID did not match.","5c6380fe32856d03e41570db", m_oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {
                Assert.fail("Repository returned error when shouldn't have.");
            }
        });

    }

    @Test
    public void loadTodaysLog_existsAndFinished_ReturnExistsAndDoesNothing() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\n" +
                        "    \"Id\": \"5c6380fe32856d03e41570db\",\n" +
                        "    \"idString\": \"5c6380fe32856d03e41570db\",\n" +
                        "    \"name\": \"02/12/2019\",\n" +
                        "    \"date\": \"2019-02-12T12:00:00Z\",\n" +
                        "    \"HealthRating\": 1,\n" +
                        "    \"BBD\": false,\n" +
                        "    \"UsedFlonase\": false,\n" +
                        "    \"FlonaseReasoning\": 0,\n" +
                        "    \"HadHeadache\": false,\n" +
                        "    \"WorkDay\": false,\n" +
                        "    \"WorkRating\": 0,\n" +
                        "    \"PersonalDayRating\": 1,\n" +
                        "    \"MindfulMoment\": \"mindful moment\",\n" +
                        "    \"OverallNotes\": \"overall notes\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLogByDate(eq("02/12/2019"), any(RepositoryCallback.class));

        setUpFailResponseForNotificationTests();

        m_oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Assert.assertFalse("DailyLogLogic said to continue when it shouldn't.",m_oLogic.continueToLog());
                Assert.assertEquals("Existing DailyLog ID is null but shouldn't be.", "5c6380fe32856d03e41570db", m_oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {
                Assert.fail("Repository returned error when shouldn't have.");
            }
        });
    }

    @Test
    public void loadTodaysLog_existsAndFinishedWithoutMindfulMoment_ReturnExistsAndDoesNothing() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\n" +
                        "    \"Id\": \"5c6380fe32856d03e41570db\",\n" +
                        "    \"idString\": \"5c6380fe32856d03e41570db\",\n" +
                        "    \"name\": \"02/12/2019\",\n" +
                        "    \"date\": \"2019-02-12T12:00:00Z\",\n" +
                        "    \"HealthRating\": 1,\n" +
                        "    \"BBD\": false,\n" +
                        "    \"UsedFlonase\": false,\n" +
                        "    \"FlonaseReasoning\": 0,\n" +
                        "    \"HadHeadache\": false,\n" +
                        "    \"WorkDay\": false,\n" +
                        "    \"WorkRating\": 0,\n" +
                        "    \"PersonalDayRating\": 1,\n" +
                        "    \"MindfulMoment\": \"\",\n" +
                        "    \"OverallNotes\": \"overall notes\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLogByDate(eq("02/12/2019"), any(RepositoryCallback.class));

        setUpFailResponseForNotificationTests();

        m_oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Assert.assertFalse("DailyLogLogic said to continue when it shouldn't.", m_oLogic.continueToLog());
                Assert.assertEquals("Existing DailyLog ID is null but shouldn't be.", "5c6380fe32856d03e41570db", m_oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {
                Assert.fail("Repository returned error when shouldn't have.");
            }
        });

    }

    @Test
    public void loadTodaysLog_existsButRatingsNotFilledIn_ReturnExistsAndOpensLog() {
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
                        "    \"MindfulMoment\": \"mindful moment\",\n" +
                        "    \"OverallNotes\": \"overall notes\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLogByDate(eq("02/12/2019"), any(RepositoryCallback.class));

        setUpFailResponseForNotificationTests();

        m_oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Assert.assertTrue("DailyLogLogic said not to continue when it should.", m_oLogic.continueToLog());
                Assert.assertEquals("Existing DailyLog ID is null but shouldn't be.", "5c6380fe32856d03e41570db", m_oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Test
    public void loadTodaysLog_existsAndPartiallyFinished_ReturnsExistsAndOpensLog() {
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
                        "    \"MindfulMoment\": \"mindful moment\",\n" +
                        "    \"OverallNotes\": \"\"\n" +
                        "}");
                return null;
            }
        }).when(m_repository).LoadDailyLogByDate(eq("02/12/2019"), any(RepositoryCallback.class));

        setUpFailResponseForNotificationTests();

        m_oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Assert.assertTrue("DailyLogLogic said not to continue when it should.", m_oLogic.continueToLog());
                Assert.assertEquals("Existing DailyLog ID is null but shouldn't be.", "5c6380fe32856d03e41570db", m_oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {
                Assert.fail("Repository returned error when shouldn't have.");
            }
        });

    }

    private void setUpFailResponseForNotificationTests() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onError(null);
                return null;
            }
        }).when(m_repository).LoadDailyLogByDate(AdditionalMatchers.not(eq("02/12/2019")), any(RepositoryCallback.class));
    }

}
