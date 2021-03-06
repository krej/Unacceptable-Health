package beer.unacceptable.unacceptablehealth;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Calendar;

import beer.unacceptable.unacceptablehealth.Controllers.CreateGoalController;
import beer.unacceptable.unacceptablehealth.Controllers.IDateLogic;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.PendingGoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CreateGoalControllerTests {
    private CreateGoalController m_oController;
    private CreateGoalController.View m_view;
    private IDateLogic m_date;
    private IRepository m_repo;
    private ILibraryRepository m_libraryRepo;

    private WorkoutType arms, run, abs, rest;

    @Before
    public void setup() {
        m_date = mock(IDateLogic.class);
        m_view = mock(CreateGoalController.View.class);
        m_repo = mock(IRepository.class);
        m_libraryRepo = mock(ILibraryRepository.class);
        m_oController = new CreateGoalController(m_view, m_date, m_repo, m_libraryRepo);

        arms = new WorkoutType();
        arms.name = "Arms";

        run = new WorkoutType();
        run.name = "Run";

        abs = new WorkoutType();
        abs.name = "Abs";

        rest = new WorkoutType();
        rest.name = "Rest";

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RepositoryCallback callback = invocation.getArgument(0);
                callback.onSuccess("[\n" +
                        "    {\n" +
                        "        \"Id\": \"5c6f8777f293eb1db4da1e61\",\n" +
                        "        \"idString\": \"5c6f8777f293eb1db4da1e61\",\n" +
                        "        \"name\": \"Run\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Id\": \"5c6f8938f293eb1db4da1e62\",\n" +
                        "        \"idString\": \"5c6f8938f293eb1db4da1e62\",\n" +
                        "        \"name\": \"Arms\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Id\": \"5c6f89a7f293eb1db4da1e63\",\n" +
                        "        \"idString\": \"5c6f89a7f293eb1db4da1e63\",\n" +
                        "        \"name\": \"Abs\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Id\": \"5c73423c230acd3cf8f11aec\",\n" +
                        "        \"idString\": \"5c73423c230acd3cf8f11aec\",\n" +
                        "        \"name\": \"Rest Day\"\n" +
                        "    }\n" +
                        "]");
                return null;
            }
        }).when(m_repo).LoadAllWorkoutTypes(any(RepositoryCallback.class));

        m_oController.loadWorkoutTypes();
    }

    private Calendar getHardCodedDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, Calendar.FEBRUARY); //changed to Calendar.FEBRUARY because its off by one
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.HOUR_OF_DAY, 7); //hard code an actual time because thats what new Date() returns when you run the program
        calendar.set(Calendar.MINUTE, 36);
        calendar.set(Calendar.SECOND, 24);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
        //Date dt = calendar.getTime();
        //return dt;
    }

    private Calendar createDate(int month, int day, int year) {
        return Tools.createDate(month, day, year, 7, 36, 24);
    }



    @Test
    public void setDateCalledForStartDate_StartDateStringPassedToUI() {
        Calendar calendar = getHardCodedDate();

        m_oController.setDate(calendar, CreateGoalController.DateType.Start);
        verify(m_view).setStartDateText(eq("Tue, February 12, 2019"));
        verify(m_view, never()).setEndDateText(anyString());
    }

    @Test
    public void setDateCalledForEndDate_EndDateStringPassedToUI() {
        Calendar calendar = getHardCodedDate();

        m_oController.setDate(calendar, CreateGoalController.DateType.End);

        verify(m_view).setEndDateText(eq("Tue, February 12, 2019"));
        verify(m_view, never()).setStartDateText(anyString());
    }

    /*@Test
    public void screenLoads_StartAndEndDateSetToTodaysDate() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return getHardCodedDate();
            }
        }).when(m_date).getTodaysDate();
    }*/

    @Test
    public void noGoalItems_checkBasedOnWeekSwitch_7GoalItemsBasedOnWeekAppear() {
        m_oController.changeGoalType(true);

        Goal g = m_oController.getGoal();
        ArrayList<PendingGoalItem> p = m_oController.getPendingGoalItems();
        Assert.assertEquals(7, p.size());
        Assert.assertEquals("Monday", p.get(0).Day);
        Assert.assertEquals("Tuesday", p.get(1).Day);
        Assert.assertEquals("Wednesday", p.get(2).Day);
        Assert.assertEquals("Thursday", p.get(3).Day);
        Assert.assertEquals("Friday", p.get(4).Day);
        Assert.assertEquals("Saturday", p.get(5).Day);
        Assert.assertEquals("Sunday", p.get(6).Day);

        verify(m_view).setGoalItems(eq(p));
    }

    @Test
    public void noGoalItems_SwitchToBasedOnWeekThenBack_NoGoalItems() {
        m_oController.changeGoalType(true);

        ArrayList<PendingGoalItem> p = m_oController.getPendingGoalItems();

        Assert.assertEquals(7, p.size());
        verify(m_view).setGoalItems(eq(p));

        m_oController.changeGoalType(false);
        p = m_oController.getPendingGoalItems();

        Assert.assertEquals(0, p.size());
        verify(m_view).setGoalItems(new ArrayList<PendingGoalItem>());
    }

    @Test
    public void hasSomeGoalItems_SwitchToBasedOnWeek_has7GoalItems() {
        ArrayList<PendingGoalItem> pendingGoalItems = new ArrayList<>();
        PendingGoalItem p = new PendingGoalItem();
        p.Day = "";
        p.WorkoutType = arms;

        PendingGoalItem p1 = new PendingGoalItem();
        p1.WorkoutType = run;

        pendingGoalItems.add(p);
        pendingGoalItems.add(p1);

        m_oController.setPendingGoalItems(pendingGoalItems);
        m_oController.changeGoalType(true);
        pendingGoalItems = m_oController.getPendingGoalItems();

        Assert.assertEquals("Monday", pendingGoalItems.get(0).Day);
        Assert.assertEquals("Arms", pendingGoalItems.get(0).WorkoutType.name);
        Assert.assertEquals("Tuesday", pendingGoalItems.get(1).Day);
        Assert.assertEquals("Run", pendingGoalItems.get(1).WorkoutType.name);
        Assert.assertEquals("Wednesday", pendingGoalItems.get(2).Day);
        Assert.assertEquals("Thursday", pendingGoalItems.get(3).Day);
        Assert.assertEquals("Friday", pendingGoalItems.get(4).Day);
        Assert.assertEquals("Saturday", pendingGoalItems.get(5).Day);
        Assert.assertEquals("Sunday", pendingGoalItems.get(6).Day);
    }

    @Test
    public void hasSomeGoalItems_SwitchToBasedOnWeek_has7GoalItems_switchBack_HasOldGoalItems() {
        ArrayList<PendingGoalItem> pendingGoalItems = new ArrayList<>();
        ArrayList<PendingGoalItem> oldGoalItems = new ArrayList<>();
        PendingGoalItem p = new PendingGoalItem();
        p.Day = "";
        p.WorkoutType = arms;

        PendingGoalItem p1 = new PendingGoalItem();
        p1.WorkoutType = run;

        pendingGoalItems.add(p);
        pendingGoalItems.add(p1);
        oldGoalItems.add(p);
        oldGoalItems.add(p1);

        m_oController.setPendingGoalItems(pendingGoalItems);
        m_oController.changeGoalType(true);
        pendingGoalItems = m_oController.getPendingGoalItems();

        Assert.assertEquals("Monday", pendingGoalItems.get(0).Day);
        Assert.assertEquals("Arms", pendingGoalItems.get(0).WorkoutType.name);
        Assert.assertEquals("Tuesday", pendingGoalItems.get(1).Day);
        Assert.assertEquals("Run", pendingGoalItems.get(1).WorkoutType.name);
        Assert.assertEquals("Wednesday", pendingGoalItems.get(2).Day);
        Assert.assertEquals("Thursday", pendingGoalItems.get(3).Day);
        Assert.assertEquals("Friday", pendingGoalItems.get(4).Day);
        Assert.assertEquals("Saturday", pendingGoalItems.get(5).Day);
        Assert.assertEquals("Sunday", pendingGoalItems.get(6).Day);

        m_oController.changeGoalType(false);
        pendingGoalItems = m_oController.getPendingGoalItems();

        Assert.assertEquals(2, pendingGoalItems.size());
        Assert.assertEquals("Arms", pendingGoalItems.get(0).WorkoutType.name);
        Assert.assertEquals("Run", pendingGoalItems.get(1).WorkoutType.name);

        verify(m_view).setGoalItems(eq(oldGoalItems));
    }

    @Test
    public void castListableObejctFromADapter_NoException() {
        ArrayList<ListableObject> ds = new ArrayList<>();
        PendingGoalItem p = new PendingGoalItem();
        p.name = "Hello";
        ds.add(p);
        Assert.assertEquals(1, ds.size());

        m_oController.setPendingGoalItemsFromAdapter(ds);
    }

    @Test
    public void eightPendingGoalItems_SwitchToWeekMode_OnlySevenRemain() {
        ArrayList<PendingGoalItem> p = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            PendingGoalItem item = new PendingGoalItem();
            item.WorkoutType = arms;
            p.add(item);
        }

        m_oController.setPendingGoalItems(p);
        m_oController.changeGoalType(true);
        Assert.assertEquals(7, m_oController.getPendingGoalItems().size());
    }

    @Test
    public void pendingGoalItemsFilledOut_notBasedOnWeek_CreateGoalItems() {
        ArrayList<PendingGoalItem> pgi = new ArrayList<>();
        PendingGoalItem p = new PendingGoalItem();
        p.WorkoutType = arms;
        pgi.add(p);

        p = new PendingGoalItem();
        p.WorkoutType = run;
        pgi.add(p);

        p = new PendingGoalItem();
        p.WorkoutType = abs;
        pgi.add(p);

        m_oController.setPendingGoalItems(pgi);
        m_oController.setDate(createDate(Calendar.MARCH,1,2019), CreateGoalController.DateType.Start);
        m_oController.setDate(createDate(Calendar.MARCH, 10, 2019), CreateGoalController.DateType.End);
        m_oController.createGoalItems(false);
        ArrayList<GoalItem> goalItems = m_oController.getGoalItems();

        Assert.assertEquals("Goal Item list not correct length",10, goalItems.size());

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 1, 2019).getTime(), goalItems.get(0).Date));
        Assert.assertFalse(goalItems.get(0).Completed);
        Assert.assertEquals(goalItems.get(0).WorkoutType, arms);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 2, 2019).getTime(), goalItems.get(1).Date));
        Assert.assertFalse(goalItems.get(1).Completed);
        Assert.assertEquals(goalItems.get(1).WorkoutType, run);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 3, 2019).getTime(), goalItems.get(2).Date));
        Assert.assertFalse(goalItems.get(2).Completed);
        Assert.assertEquals(goalItems.get(2).WorkoutType, abs);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 4, 2019).getTime(), goalItems.get(3).Date));
        Assert.assertFalse(goalItems.get(3).Completed);
        Assert.assertEquals(goalItems.get(3).WorkoutType, arms);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 5, 2019).getTime(), goalItems.get(4).Date));
        Assert.assertFalse(goalItems.get(4).Completed);
        Assert.assertEquals(goalItems.get(4).WorkoutType, run);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 6, 2019).getTime(), goalItems.get(5).Date));
        Assert.assertFalse(goalItems.get(5).Completed);
        Assert.assertEquals(goalItems.get(5).WorkoutType, abs);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 7, 2019).getTime(), goalItems.get(6).Date));
        Assert.assertFalse(goalItems.get(6).Completed);
        Assert.assertEquals(goalItems.get(6).WorkoutType, arms);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 8, 2019).getTime(), goalItems.get(7).Date));
        Assert.assertFalse(goalItems.get(7).Completed);
        Assert.assertEquals(goalItems.get(7).WorkoutType, run);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 9, 2019).getTime(), goalItems.get(8).Date));
        Assert.assertFalse(goalItems.get(8).Completed);
        Assert.assertEquals(goalItems.get(8).WorkoutType, abs);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 10, 2019).getTime(), goalItems.get(9).Date));
        Assert.assertFalse(goalItems.get(9).Completed);
        Assert.assertEquals(goalItems.get(9).WorkoutType, arms);


    }

    @Test
    public void pendingGoalItemsFilledOut_basedOnWeek_CreateGoalItems() {
        ArrayList<PendingGoalItem> pgi = new ArrayList<>();
        PendingGoalItem p = new PendingGoalItem();
        p.Day = "Monday";
        p.WorkoutType = arms;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Tuesday";
        p.WorkoutType = run;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Wednesday";
        p.WorkoutType = abs;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Thursday";
        p.WorkoutType = run;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Thursday";
        p.WorkoutType = abs;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Friday";
        p.WorkoutType = arms;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Saturday";
        p.WorkoutType = abs;
        pgi.add(p);

        p = new PendingGoalItem();
        p.Day = "Sunday";
        p.WorkoutType = rest;
        pgi.add(p);

        m_oController.setPendingGoalItems(pgi);
        m_oController.setDate(createDate(Calendar.MARCH,1,2019), CreateGoalController.DateType.Start);
        m_oController.setDate(createDate(Calendar.MARCH, 9, 2019), CreateGoalController.DateType.End);
        m_oController.createGoalItems(true);
        ArrayList<GoalItem> goalItems = m_oController.getGoalItems();

        Assert.assertEquals("Goal Item list not correct length",10, goalItems.size());

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 1, 2019).getTime(), goalItems.get(0).Date));
        Assert.assertFalse(goalItems.get(0).Completed);
        Assert.assertEquals(goalItems.get(0).WorkoutType, arms);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 2, 2019).getTime(), goalItems.get(1).Date));
        Assert.assertFalse(goalItems.get(1).Completed);
        Assert.assertEquals(goalItems.get(1).WorkoutType, abs);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 3, 2019).getTime(), goalItems.get(2).Date));
        Assert.assertFalse(goalItems.get(2).Completed);
        Assert.assertEquals(goalItems.get(2).WorkoutType, rest);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 4, 2019).getTime(), goalItems.get(3).Date));
        Assert.assertFalse(goalItems.get(3).Completed);
        Assert.assertEquals(goalItems.get(3).WorkoutType, arms);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 5, 2019).getTime(), goalItems.get(4).Date));
        Assert.assertFalse(goalItems.get(4).Completed);
        Assert.assertEquals(goalItems.get(4).WorkoutType, run);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 6, 2019).getTime(), goalItems.get(5).Date));
        Assert.assertFalse(goalItems.get(5).Completed);
        Assert.assertEquals(goalItems.get(5).WorkoutType, abs);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 7, 2019).getTime(), goalItems.get(6).Date));
        Assert.assertFalse(goalItems.get(6).Completed);
        Assert.assertEquals(goalItems.get(6).WorkoutType, run);
        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 7, 2019).getTime(), goalItems.get(7).Date));
        Assert.assertFalse(goalItems.get(7).Completed);
        Assert.assertEquals(goalItems.get(7).WorkoutType, abs);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 8, 2019).getTime(), goalItems.get(8).Date));
        Assert.assertFalse(goalItems.get(8).Completed);
        Assert.assertEquals(goalItems.get(8).WorkoutType, arms);

        Assert.assertTrue(Tools.CompareDatesWithoutTime(createDate(Calendar.MARCH, 9, 2019).getTime(), goalItems.get(9).Date));
        Assert.assertFalse(goalItems.get(9).Completed);
        Assert.assertEquals(goalItems.get(9).WorkoutType, abs);
    }

    @Test
    public void saveWithNoData_ShowErrors() {
        m_oController.saveGoal("", "", false, null, 0, null);

        verify(m_view).showDescriptionError();
        verify(m_view).showNameError();
        verify(m_view).showStartDateError();
        verify(m_view).showEndDateError(eq("This field is required"));
        verify(m_libraryRepo, never()).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
    }

    @Test
    public void saveWithEndDateBeforeStartDate_ShowError() {
        m_oController.setDate(createDate(Calendar.MARCH, 1, 2019), CreateGoalController.DateType.Start);
        m_oController.setDate(createDate(Calendar.FEBRUARY,1,2019), CreateGoalController.DateType.End);

        m_oController.saveGoal("", "", false, null, 0, null);

        verify(m_view).showEndDateError(eq("End Date must come after Start Date"));
        verify(m_libraryRepo, never()).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
    }

    @Test
    public void noPendingGoalItems_SwitchToWeekBased_AllPendingGoalItemsHaveWorkoutType() {
        ArrayList<PendingGoalItem> pgi = new ArrayList<>();

        m_oController.setPendingGoalItems(pgi);
        m_oController.changeGoalType(true);

        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(0).WorkoutType.name);
        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(1).WorkoutType.name);
        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(2).WorkoutType.name);
        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(3).WorkoutType.name);
        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(4).WorkoutType.name);
        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(5).WorkoutType.name);
        Assert.assertEquals("Run", m_oController.getPendingGoalItems().get(6).WorkoutType.name);
    }

    @Test
    public void pendingGoalItemsNotBasedOnWeek_Save_GoalSaves() {
        m_oController.setDate(createDate(Calendar.APRIL, 1, 2019), CreateGoalController.DateType.Start);
        m_oController.setDate(createDate(Calendar.APRIL,30,2019), CreateGoalController.DateType.End);

        ArrayList<ListableObject> pendingGoalItems = new ArrayList<>();
        PendingGoalItem p = new PendingGoalItem();
        p.WorkoutType = arms;
        pendingGoalItems.add(p);

        p = new PendingGoalItem();
        p.WorkoutType = abs;
        pendingGoalItems.add(p);

        p = new PendingGoalItem();
        p.WorkoutType = arms;
        pendingGoalItems.add(p);

        p = new PendingGoalItem();
        p.WorkoutType = rest;
        pendingGoalItems.add(p);

        m_oController.saveGoal("Test goal", "Test description", false, pendingGoalItems, 0, null);

        verify(m_libraryRepo).Save(anyString(), any(byte[].class), any(RepositoryCallback.class));
    }
}
