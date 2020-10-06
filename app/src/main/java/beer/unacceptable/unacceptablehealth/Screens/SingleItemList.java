package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.BaseAdapterViewControl;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Adapters.SingleItemViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.SingleItemListController;
import beer.unacceptable.unacceptablehealth.Models.Exercise;
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
                m_Adapter.showAddItemDialog(SingleItemList.this, m_rvList.getContext(), SingleItemViewControl.ADD_ITEM, null);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        final String sCollectionName = bundle.getString("collectionName");
        int iDialogLayout = bundle.getInt("dialogLayout", R.layout.dialog_edit_ingredient);
        int iItemLayout = bundle.getInt("itemLayout", R.layout.one_line_list);
        ListableObject[] data = (ListableObject[])bundle.getSerializable("data");
        boolean bAddHorizontalSpacing = bundle.getBoolean("addHorizontalSpacing", false);

        String sTitle = bundle.getString("title");

        BaseAdapterViewControl viewControl = (BaseAdapterViewControl) bundle.getSerializable("viewControl");
        viewControl.m_Activity = this;

        setTitle(sTitle);

        m_oController = new SingleItemListController(new Repository(), new LibraryRepository(), sCollectionName);
        m_oController.attachView(this);

        m_rvList = findViewById(R.id.list);
        m_SwipeRefresh = findViewById(R.id.swiperefresh);

        m_Adapter = Tools.setupRecyclerView(m_rvList, getApplicationContext(), iItemLayout, iDialogLayout, false, viewControl, true, bAddHorizontalSpacing);

        final boolean bEnableSwipeRefresh = data == null;

            m_SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (bEnableSwipeRefresh)
                        m_oController.LoadCollection(sCollectionName, true);
                    else
                        m_SwipeRefresh.setRefreshing(false);
                }
            });

        if (bEnableSwipeRefresh) {
            m_oController.LoadCollection(sCollectionName, false);
        } else {
            PopulateList(data, false);
        }
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

    @Override
    public void onRestart() {
        super.onRestart();
        //Tools.ShowToast(getApplicationContext(), "OnRestart", Toast.LENGTH_LONG);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SingleItemViewControl.EDIT_ITEM:
                    String sDeleteID = data.getStringExtra("IDString");
                    boolean bDeleted = data.getBooleanExtra("deleted", false);

                    if (bDeleted) {
                        for (int i = 0; i < m_Adapter.size(); i++) {
                            if (m_Adapter.get(i).idString.equals(sDeleteID)) {
                                m_Adapter.remove(i);
                            }
                        }
                    }
                    break;

                case SingleItemViewControl.ADD_ITEM:
                    Exercise e = new Exercise();
                    e.idString = data.getStringExtra("IDString");
                    e.name = data.getStringExtra("name");
                    m_Adapter.add(e);
            }
        }

    }
}
