package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Adapters.GoalItemAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Controllers.ViewGoalController;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.Models.GoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewGoal
        extends BaseActivity
    implements ViewGoalController.View
        {

    //A lot of this is copy and pasted from CreateGoal. Maybe I should combine these? I don't know...
    TextView m_etStartDate;
    TextView m_etEndDate;
    TextView m_tvName;
    TextView m_etBriefDescription;
    Spinner m_spGoalAmountType;
    EditText m_etGoalAmount;
    ViewPager m_viewPager;
    TabLayout m_TabLayout;
    SwipeRefreshLayout m_SwipeRefresh;

    private SectionsPagerAdapter m_SectionsPagerAdapter;

    RecyclerView m_rvGoalItems;
    NewAdapter m_Adapter;

    ViewGoalController m_oController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FindUIElements();

        m_SectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        m_viewPager.setAdapter(m_SectionsPagerAdapter);
        m_TabLayout.setupWithViewPager(m_viewPager);

        m_oController = new ViewGoalController(new Repository());
        m_oController.attachView(this);
        m_oController.loadWorkoutTypes();

        LockDownScreen();

        //m_Adapter = Tools.setupRecyclerView(m_rvGoalItems, getApplicationContext(), R.layout.list_goal_item, 0, false, new GoalItemAdapterViewControl(true, true));

        Intent intent = getIntent();
        final String sIdString = intent.getStringExtra("id");
        m_oController.loadGoal(sIdString, false);

        m_SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                m_oController.loadGoal(sIdString, true);
            }
        });
    }

    private void LockDownScreen() {
        m_etGoalAmount.setEnabled(false);
        m_etBriefDescription.setEnabled(false);
        m_tvName.setEnabled(false);
        m_spGoalAmountType.setEnabled(false);
    }

    private void FindUIElements() {
        m_etStartDate = findViewById(R.id.goal_start_date);
        m_etEndDate = findViewById(R.id.goal_end_date);
        m_rvGoalItems = findViewById(R.id.goal_items);
        m_etBriefDescription = findViewById(R.id.goal_description);
        m_tvName = findViewById(R.id.goal_name);
        m_spGoalAmountType = findViewById(R.id.goal_amount_type);
        m_etGoalAmount = findViewById(R.id.goal_amount);
        m_viewPager = findViewById(R.id.goalitems_view_pager);
        m_TabLayout = findViewById(R.id.goalitem_tablayout);
        m_SwipeRefresh = findViewById(R.id.swiperefresh);
    }

    @Override
    public void PopulateScreen(Goal goal, boolean bRefreshing) {
        m_tvName.setText(goal.name);
        m_etStartDate.setText(goal.StartDateFormatted());
        m_etEndDate.setText(goal.EndDateFormatted());
        m_etBriefDescription.setText(goal.Description);
        m_etGoalAmount.setText(String.valueOf(goal.OverallGoalAmount));
        try {
            m_spGoalAmountType.setSelection(Arrays.asList(m_oController.getWorkoutTypes()).indexOf(goal.OverallGoalAmountType));
        } catch (Exception ex) {
            //quick error checking for now
            //TODO: Have it try to set this after the workout types are loaded. will need to handle scenario where it loads those first before the Goal though
            Tools.ShowToast(getApplicationContext(), "Workout Types not loaded quick enough", Toast.LENGTH_SHORT);
        }
        m_SectionsPagerAdapter.ClearGoalItems();
        m_SectionsPagerAdapter.PopulateGoalItems(goal.GoalItems);
        /*for(GoalItem goalItem : goal.GoalItems) {
            m_Adapter.add(goalItem);
        }*/

        if (bRefreshing) {
            m_SwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void PopulateWorkoutTypeDropDown(WorkoutType[] workoutTypes) {
        ArrayAdapter<WorkoutType> aa = new ArrayAdapter<>(m_spGoalAmountType.getContext(), android.R.layout.simple_spinner_dropdown_item, workoutTypes);
        m_spGoalAmountType.setAdapter(aa);
    }


            /**** tab stuff here ***/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        GoalItemsFragment m_oUpcomingFragment;
        GoalItemsFragment m_oAllFragment;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    if (m_oUpcomingFragment == null)
                        m_oUpcomingFragment = GoalItemsFragment.newInstance(1);
                    return m_oUpcomingFragment;
                case 1:
                    if (m_oAllFragment == null)
                        m_oAllFragment = GoalItemsFragment.newInstance(1);
                    return m_oAllFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "UPCOMING";
                case 1:
                    return "ALL";
            }

            return null;
        }

        public void PopulateGoalItems(ArrayList<GoalItem> goalItems) {
            m_oUpcomingFragment.PopulateGoalItems(goalItems, false);
            m_oAllFragment.PopulateGoalItems(goalItems, true);
        }

        public void ClearGoalItems() {
            m_oUpcomingFragment.ClearGoalItems();
            m_oAllFragment.ClearGoalItems();
        }
    }

    public static class GoalItemsFragment extends Fragment
    implements MainScreenController.View{
        private RecyclerView m_rvGoalItems;
        private NewAdapter m_Adapter;

        public GoalItemsFragment() {}

        public static GoalItemsFragment newInstance(int sectionNumber) {
            GoalItemsFragment fragment = new GoalItemsFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //TODO: Maybe? content_goal_list was the first just recycler view i could find
            View rootView = inflater.inflate(R.layout.content_daily_log_list, container, false);

            m_rvGoalItems = rootView.findViewById(R.id.dailyLogList);
            m_Adapter = Tools.setupRecyclerView(m_rvGoalItems, rootView.getContext(), R.layout.list_goal_item, 0, false, new GoalItemAdapterViewControl(true, true, this));

            return rootView;
        }

        public void PopulateGoalItems(ArrayList<GoalItem> goalItems, boolean bShowPastGoalItems) {
            Date dt = new Date();
            for (GoalItem gi : goalItems) {
                //TODO: Put this logic in a controller so i can test it

                if (bShowPastGoalItems || gi.Date.after(dt) || (Tools.CompareDatesWithoutTime(gi.Date, dt) && !gi.Completed) )
                m_Adapter.add(gi);
            }
        }

        public void ClearGoalItems() {
            m_Adapter.clear();
        }

        @Override
        public void showTodaysLog(boolean b) {

        }

        @Override
        public void showNewLogButton(boolean b) {

        }

        @Override
        public void populateTodaysLog(DailyLog dl) {

        }

        @Override
        public void showDailyLogError() {

        }

        @Override
        public void populateTodaysGoalItems(GoalItem[] goalItems) {

        }

        @Override
        public void setGoalItemsVisibility(boolean bVisible) {

        }

        @Override
        public void setNoGoalLabelVisibility(boolean bVisible) {

        }

        @Override
        public void showToast(String sMessage) {
            Tools.ShowToast(getContext(), sMessage, Toast.LENGTH_LONG);
        }
    }
}
