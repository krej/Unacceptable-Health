package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Adapters.ExerciseDatabaseViewControl;
import beer.unacceptable.unacceptablehealth.R;

public class ExerciseDatabase extends BaseActivity {

    private RecyclerView m_rvSetup;
    private NewAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_rvSetup = (RecyclerView)findViewById(R.id.exercise_setup);
        m_Adapter = Tools.setupRecyclerView(m_rvSetup, getApplicationContext(), R.layout.one_line_list, 0, false, new ExerciseDatabaseViewControl(), true);
        //m_rvSetup.addItemDecoration(new HoriztonalSpaceItemDecoration(Tools.SAMSUNG_CURVED_SCREEN_PADDING));

        LoadSetupItems();
    }

    private void LoadSetupItems() {
        m_Adapter.add(new ListableObject(getString(R.string.workout_types)));
        m_Adapter.add(new ListableObject(getString(R.string.exercises)));
        m_Adapter.add(new ListableObject(getString(R.string.muscles)));
    }
}
