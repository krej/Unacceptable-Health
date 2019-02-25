package beer.unacceptable.unacceptablehealth;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Controllers.CreateGoalController;
import beer.unacceptable.unacceptablehealth.Controllers.IDateLogic;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.PendingGoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

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

    private WorkoutType arms, run, abs;

    @Before
    public void setup() {
        m_date = mock(IDateLogic.class);
        m_view = mock(CreateGoalController.View.class);
        m_repo = mock(IRepository.class);
        m_oController = new CreateGoalController(m_view, m_date, m_repo);

        arms = new WorkoutType();
        arms.name = "Arms";

        run = new WorkoutType();
        run.name = "Run";

        abs = new WorkoutType();
        abs.name = "Abs";
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
        p.Type = arms;

        PendingGoalItem p1 = new PendingGoalItem();
        p1.Type = run;

        pendingGoalItems.add(p);
        pendingGoalItems.add(p1);

        m_oController.setPendingGoalItems(pendingGoalItems);
        m_oController.changeGoalType(true);
        pendingGoalItems = m_oController.getPendingGoalItems();

        Assert.assertEquals("Monday", pendingGoalItems.get(0).Day);
        Assert.assertEquals("Arms", pendingGoalItems.get(0).Type.name);
        Assert.assertEquals("Tuesday", pendingGoalItems.get(1).Day);
        Assert.assertEquals("Run", pendingGoalItems.get(1).Type.name);
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
        p.Type = arms;

        PendingGoalItem p1 = new PendingGoalItem();
        p1.Type = run;

        pendingGoalItems.add(p);
        pendingGoalItems.add(p1);
        oldGoalItems.add(p);
        oldGoalItems.add(p1);

        m_oController.setPendingGoalItems(pendingGoalItems);
        m_oController.changeGoalType(true);
        pendingGoalItems = m_oController.getPendingGoalItems();

        Assert.assertEquals("Monday", pendingGoalItems.get(0).Day);
        Assert.assertEquals("Arms", pendingGoalItems.get(0).Type.name);
        Assert.assertEquals("Tuesday", pendingGoalItems.get(1).Day);
        Assert.assertEquals("Run", pendingGoalItems.get(1).Type.name);
        Assert.assertEquals("Wednesday", pendingGoalItems.get(2).Day);
        Assert.assertEquals("Thursday", pendingGoalItems.get(3).Day);
        Assert.assertEquals("Friday", pendingGoalItems.get(4).Day);
        Assert.assertEquals("Saturday", pendingGoalItems.get(5).Day);
        Assert.assertEquals("Sunday", pendingGoalItems.get(6).Day);

        m_oController.changeGoalType(false);
        pendingGoalItems = m_oController.getPendingGoalItems();

        Assert.assertEquals(2, pendingGoalItems.size());
        Assert.assertEquals("Arms", pendingGoalItems.get(0).Type.name);
        Assert.assertEquals("Run", pendingGoalItems.get(1).Type.name);

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
            item.Type = arms;
            p.add(item);
        }

        m_oController.setPendingGoalItems(p);
        m_oController.changeGoalType(true);
        Assert.assertEquals(7, m_oController.getPendingGoalItems().size());
    }
}
