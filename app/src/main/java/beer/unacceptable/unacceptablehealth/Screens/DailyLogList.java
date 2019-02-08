package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Adapters.DailyLogAdapter;
import beer.unacceptable.unacceptablehealth.Adapters.RecipeAdapter;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.Models.FoodRecipe;
import beer.unacceptable.unacceptablehealth.R;

public class DailyLogList extends AppCompatActivity {

    private RecyclerView m_rvDailyLogs;
    private DailyLogAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        m_rvDailyLogs = (RecyclerView)findViewById(R.id.dailyLogList);
        m_rvDailyLogs.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvDailyLogs.setLayoutManager(m_LayoutManager);


        m_Adapter = new DailyLogAdapter(R.layout.default_list, 0);
        LoadDailyLogs();

        m_rvDailyLogs.setAdapter(m_Adapter);
    }

    private void LoadDailyLogs() {
        Network.WebRequest(Request.Method.GET, Tools.HealthAPIURL() + "/dailylog", null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        DailyLog[] logs = gson.fromJson(response, DailyLog[].class);

                        for (DailyLog dl : logs) {
                            m_Adapter.add(dl);
                        }

                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work " + error.getMessage());
                String errorMsg = "";
                try {
                    errorMsg = error.getCause().getMessage();
                } catch(Exception e) {

                }
                Tools.ShowToast(getApplicationContext(), "Failed to load daily logs: " + errorMsg, Toast.LENGTH_LONG);
            }
        });
    }
}