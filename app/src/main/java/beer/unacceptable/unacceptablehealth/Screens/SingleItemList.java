package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.SingleItemListController;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class SingleItemList extends AppCompatActivity
implements SingleItemListController.View {

    private RecyclerView m_rvList;
    private NewAdapter m_Adapter;
    private SingleItemListController m_oController;
    private SwipeRefreshLayout m_SwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Adapter.showAddItemDialog(m_rvList.getContext(), null);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        final String sCollectionName = bundle.getString("collectionName");
        int iDialogLayout = bundle.getInt("dialogLayout", R.layout.dialog_edit_ingredient);

        String sTitle = bundle.getString("title");

        BaseAdapterViewControl viewControl = (BaseAdapterViewControl) bundle.getSerializable("viewControl");

        setTitle(sTitle);

        m_oController = new SingleItemListController(new Repository(), new LibraryRepository(), sCollectionName);
        m_oController.attachView(this);

        m_rvList = findViewById(R.id.list);
        m_SwipeRefresh = findViewById(R.id.swiperefresh);

        m_Adapter = Tools.setupRecyclerView(m_rvList, getApplicationContext(), R.layout.one_line_list, iDialogLayout, false, viewControl, true);

        m_SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                m_oController.LoadCollection(sCollectionName, true);
            }
        });

        m_oController.LoadCollection(sCollectionName, false);
    }

    @Override
    public void PopulateList(ListableObject[] objects, boolean bRefreshing) {
        m_Adapter.clear();

        for (ListableObject t : objects) {
            m_Adapter.add(t);
        }

        if (bRefreshing) {
            m_SwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void ShowToast(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }
}
