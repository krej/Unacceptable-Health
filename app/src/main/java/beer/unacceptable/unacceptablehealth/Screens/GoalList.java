package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;
import com.unacceptable.unacceptablelibrary.Tools.VerticalSpaceItemDecoration;

import beer.unacceptable.unacceptablehealth.Adapters.GoalAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Models.Goal;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class GoalList extends BaseActivity {

    private RecyclerView m_rvGoalList;
    private NewAdapter m_Adapter;
    private Repository m_Repository = new Repository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FindUIItems();

        m_Adapter = Tools.setupRecyclerView(m_rvGoalList, getApplicationContext(), R.layout.list_goal, 0, false, new GoalAdapterViewControl(), true);
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(20, true);
        m_rvGoalList.addItemDecoration(verticalSpaceItemDecoration);

        m_Repository.LoadAllGoals(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                Goal goals[] = Tools.convertJsonResponseToObject(t, Goal[].class);
                for (Goal g : goals) {
                    m_Adapter.add(g);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void FindUIItems() {
        m_rvGoalList = findViewById(R.id.goal_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goallist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.create_goal:
                Intent intent = new Intent(getApplicationContext(), CreateGoal.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
