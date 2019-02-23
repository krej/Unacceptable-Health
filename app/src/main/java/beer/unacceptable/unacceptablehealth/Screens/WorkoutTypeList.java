package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;

import beer.unacceptable.unacceptablehealth.Adapters.DailyLogAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Adapters.ExerciseDatabaseViewControl;
import beer.unacceptable.unacceptablehealth.Adapters.WorkoutTypeViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.WorkoutTypeController;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class WorkoutTypeList extends AppCompatActivity
implements WorkoutTypeController.View {

    private RecyclerView m_rvWorkoutTypes;
    private NewAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;
    private WorkoutTypeController m_oController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_type_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Adapter.showAddItemDialog(m_rvWorkoutTypes.getContext(), null);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        m_oController = new WorkoutTypeController(new Repository(), new LibraryRepository());
        m_oController.attachView(this);

        m_rvWorkoutTypes = (RecyclerView)findViewById(R.id.list);
        m_rvWorkoutTypes.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvWorkoutTypes.setLayoutManager(m_LayoutManager);


        m_Adapter = new NewAdapter(R.layout.one_line_list, R.layout.dialog_edit_ingredient, false, new WorkoutTypeViewControl());
        m_oController.LoadAllWorkoutTypes();

        m_rvWorkoutTypes.setAdapter(m_Adapter);
    }

    @Override
    public void PopulateWorkoutTypes(WorkoutType[] workoutTypes) {
        for (WorkoutType t : workoutTypes) {
            m_Adapter.add(t);
        }
    }
}
