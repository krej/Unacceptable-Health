package beer.unacceptable.unacceptablehealth;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.ViewGoalController;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ViewGoalTests {

    private Repository m_repo;
    private DateLogic m_DateLogic;
    private LibraryRepository m_LibraryRepo;
    private ViewGoalController m_Controller;
    private Goal m_g = null;
    private ViewGoalController.View view;

    @Before
    public void Setup() {
        m_repo = mock(Repository.class);
        m_DateLogic = mock(DateLogic.class);
        m_LibraryRepo = mock(LibraryRepository.class);
        view = mock(ViewGoalController.View.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\n" +
                        "  \"StartDate\": \"2020-09-22T12:55:28Z\",\n" +
                        "  \"EndDate\": \"2020-09-27T12:55:28Z\",\n" +
                        "  \"Description\": \"Another one week at a time workout goal.\",\n" +
                        "  \"GoalItems\": [\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "        \"name\": \"Abs\"\n" +
                        "      },\n" +
                        "      \"Date\": \"2020-09-22T12:55:28Z\",\n" +
                        "      \"Completed\": true,\n" +
                        "      \"name\": \"Abs on Tue, September 22, 2020\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5d696eefee5d221046fd50b1\",\n" +
                        "        \"name\": \"Cardio\"\n" +
                        "      },\n" +
                        "      \"Date\": \"2020-09-23T12:55:28Z\",\n" +
                        "      \"Completed\": true,\n" +
                        "      \"name\": \"Cardio on Wed, September 23, 2020\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "        \"name\": \"Rest\"\n" +
                        "      },\n" +
                        "      \"Date\": \"2020-09-24T12:55:28Z\",\n" +
                        "      \"Completed\": true,\n" +
                        "      \"name\": \"Rest on Thu, September 24, 2020\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "        \"name\": \"Arms\"\n" +
                        "      },\n" +
                        "      \"Date\": \"2020-09-25T12:55:28Z\",\n" +
                        "      \"Completed\": false,\n" +
                        "      \"name\": \"Arms on Fri, September 25, 2020\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5caa7f8479f5dc4dfbe2fd2e\",\n" +
                        "        \"name\": \"Legs\"\n" +
                        "      },\n" +
                        "      \"Date\": \"2020-09-26T12:55:28Z\",\n" +
                        "      \"Completed\": false,\n" +
                        "      \"name\": \"Legs on Sat, September 26, 2020\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "        \"name\": \"Rest\"\n" +
                        "      },\n" +
                        "      \"Date\": \"2020-09-27T12:55:28Z\",\n" +
                        "      \"Completed\": false,\n" +
                        "      \"name\": \"Rest on Sun, September 27, 2020\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"PendingGoalItems\": [\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "        \"name\": \"Rest\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Monday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "        \"name\": \"Abs\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Tuesday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5d696eefee5d221046fd50b1\",\n" +
                        "        \"name\": \"Cardio\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Wednesday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "        \"name\": \"Rest\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Thursday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "        \"name\": \"Arms\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Friday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5caa7f8479f5dc4dfbe2fd2e\",\n" +
                        "        \"name\": \"Legs\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Saturday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"WorkoutType\": {\n" +
                        "        \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "        \"name\": \"Rest\"\n" +
                        "      },\n" +
                        "      \"Day\": \"Sunday\",\n" +
                        "      \"name\": \"PendingGoalItem\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"BasedOnWeek\": true,\n" +
                        "  \"OverallGoalAmount\": 0.0,\n" +
                        "  \"OverallGoalAmountType\": {\n" +
                        "    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "    \"name\": \"Run\"\n" +
                        "  },\n" +
                        "  \"Acheived\": false,\n" +
                        "  \"Id\": \"5f6a732b61925036718bcdee\",\n" +
                        "  \"idString\": \"5f6a732b61925036718bcdee\",\n" +
                        "  \"name\": \"Continuing the slow wagon\"\n" +
                        "}");
                return null;
            }
        }).when(m_repo).LoadGoal(eq("5f6a732b61925036718bcdee"), any(RepositoryCallback.class));

        /*m_repo.LoadGoal("5f6a732b61925036718bcdee", new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                m_g = Tools.convertJsonResponseToObject(t, Goal.class);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });*/

        m_Controller = new ViewGoalController(m_repo, m_DateLogic, m_LibraryRepo);
        m_Controller.attachView(view);
        m_Controller.loadGoal("5f6a732b61925036718bcdee", false);
    }

    @Test
    public void LoadGoal() {
        Assert.assertTrue(m_g != null);
        Assert.assertEquals(7, m_g.PendingGoalItems.size());
        Assert.assertEquals(6, m_g.GoalItems.size());
    }

    @Test
    public void ExtendGoal() {
        Goal goal = m_Controller.getGoal();

        Assert.assertEquals(6, goal.GoalItems.size());
        Calendar c = getHardCodedDate();

        m_Controller.extendGoal(c);

        Assert.assertEquals(13, goal.GoalItems.size());

        Assert.assertEquals("Rest on Mon, September 28, 2020", goal.GoalItems.get(6).name);
        Assert.assertEquals("Abs on Tue, September 29, 2020", goal.GoalItems.get(7).name);
        Assert.assertEquals("Cardio on Wed, September 30, 2020", goal.GoalItems.get(8).name);
        Assert.assertEquals("Rest on Thu, October 01, 2020", goal.GoalItems.get(9).name);
        Assert.assertEquals("Arms on Fri, October 02, 2020", goal.GoalItems.get(10).name);
        Assert.assertEquals("Legs on Sat, October 03, 2020", goal.GoalItems.get(11).name);
        Assert.assertEquals("Rest on Sun, October 04, 2020", goal.GoalItems.get(12).name);

    }

    private Calendar getHardCodedDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.HOUR_OF_DAY, 7); //hard code an actual time because thats what new Date() returns when you run the program
        calendar.set(Calendar.MINUTE, 36);
        calendar.set(Calendar.SECOND, 24);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
        //Date dt = calendar.getTime();
        //return dt;
    }
}
