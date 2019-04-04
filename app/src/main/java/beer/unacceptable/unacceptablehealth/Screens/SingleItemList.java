package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.List;

import beer.unacceptable.unacceptablehealth.Adapters.WorkoutTypeViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.SingleItemListController;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class SingleItemList extends AppCompatActivity
implements SingleItemListController.View {

    private RecyclerView m_rvWorkoutTypes;
    private NewAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;
    private SingleItemListController m_oController;

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

        String sCollectionName = getIntent().getStringExtra("collectionName");

        m_oController = new SingleItemListController(new Repository(), new LibraryRepository(), sCollectionName);
        m_oController.attachView(this);

        m_rvWorkoutTypes = (RecyclerView)findViewById(R.id.list);

        m_Adapter = Tools.setupRecyclerView(m_rvWorkoutTypes, getApplicationContext(), R.layout.one_line_list, R.layout.dialog_edit_ingredient, false, new WorkoutTypeViewControl(sCollectionName), true, true);

        //m_oController.LoadAllWorkoutTypes();
        m_oController.LoadCollection(sCollectionName);
    }

    @Override
    public void PopulateWorkoutTypes(ListableObject[] objects) {
        for (ListableObject t : objects) {
            m_Adapter.add(t);
        }
    }

    @Override
    public void ShowToast(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }
}
