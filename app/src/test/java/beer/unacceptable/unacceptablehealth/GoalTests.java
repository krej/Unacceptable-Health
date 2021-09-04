package beer.unacceptable.unacceptablehealth;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class GoalTests {
    private Repository m_repo;

    @Before
    public void setup() {
        m_repo = mock(Repository.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(1);
                callback.onSuccess("{\n" +
                        "        \"StartDate\": \"2019-03-01T12:01:55Z\",\n" +
                        "        \"EndDate\": \"2019-03-31T23:01:55Z\",\n" +
                        "        \"Description\": \"A lot of cardio to prepare for possibly getting off probation. Gotta lose that weight! So I can gain it back.\",\n" +
                        "        \"GoalItems\": [\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "                    \"name\": \"Abs\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-01T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Abs on Fri, March 01, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-02T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Sat, March 02, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-03T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Sun, March 03, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-04T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Mon, March 04, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "                    \"name\": \"Arms\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-05T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Arms on Tue, March 05, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-06T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Wed, March 06, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-12T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Tues, March 12, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "                    \"name\": \"Abs\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-11T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Abs on Mon, March 11, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-09T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Sat, March 09, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-10T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Sun, March 10, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-11T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Mon, March 11, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "                    \"name\": \"Arms\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-12T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Arms on Tue, March 12, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-13T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Wed, March 13, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-14T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Thu, March 14, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "                    \"name\": \"Abs\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-19T12:00:00Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Abs on Fri, March 15, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-16T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Sat, March 16, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-17T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Sun, March 17, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-18T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Mon, March 18, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "                    \"name\": \"Arms\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-19T12:01:55Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Arms on Tue, March 19, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-21T12:00:00Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Wed, March 20, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-20T12:00:00Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Run on Thu, March 21, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-25T12:01:55Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Run on Mon, March 25, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "                    \"name\": \"Arms\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-27T12:00:00Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Arms on Tue, March 26, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-26T12:00:00Z\",\n" +
                        "                \"Completed\": true,\n" +
                        "                \"name\": \"Rest on Wed, March 27, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-28T12:01:55Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Run on Thu, March 28, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "                    \"name\": \"Abs\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-31T12:00:00Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Abs on Fri, March 29, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-30T12:01:55Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Run on Sat, March 30, 2019\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Date\": \"2019-03-31T12:00:00Z\",\n" +
                        "                \"Completed\": false,\n" +
                        "                \"name\": \"Rest on Sun, March 31, 2019\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"PendingGoalItems\": [\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Monday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e72406ff7cdb0313a5\",\n" +
                        "                    \"name\": \"Arms\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Tuesday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Wednesday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Thursday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3ec2406ff7cdb0313a6\",\n" +
                        "                    \"name\": \"Abs\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Friday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "                    \"name\": \"Run\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Saturday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"WorkoutType\": {\n" +
                        "                    \"idString\": \"5c736c142406ff7cdb0313a9\",\n" +
                        "                    \"name\": \"Rest\"\n" +
                        "                },\n" +
                        "                \"Day\": \"Sunday\",\n" +
                        "                \"name\": \"PendingGoalItem\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"BasedOnWeek\": true,\n" +
                        "        \"OverallGoalAmount\": 35,\n" +
                        "        \"OverallGoalAmountType\": {\n" +
                        "            \"idString\": \"5c70d3e02406ff7cdb0313a4\",\n" +
                        "            \"name\": \"Run\"\n" +
                        "        },\n" +
                        "        \"Acheived\": false,\n" +
                        "        \"Id\": \"5c78b016e1eacb3b859ab1d6\",\n" +
                        "        \"idString\": \"5c78b016e1eacb3b859ab1d6\",\n" +
                        "        \"name\": \"Run a lot\"\n" +
                        "    }");
                return null;
            }
        }).when(m_repo).LoadGoal(eq("123"), any(RepositoryCallback.class));


    }

    @Test
    public void Goal14Of20Completed_Returns70Percent() {

        m_repo.LoadGoal("123", new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Goal g = Tools.convertJsonResponseToObject(t, Goal.class);
                String sCompletedPercent = g.GoalsCompletedPercent(false, false);
                Assert.assertEquals("70", sCompletedPercent);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
