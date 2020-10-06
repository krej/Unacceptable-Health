package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Objects;

import beer.unacceptable.unacceptablehealth.Adapters.DailyLogAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;

public class DailyLogList extends AppCompatActivity {

    private RecyclerView m_rvDailyLogs;
    private NewAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        m_rvDailyLogs = (RecyclerView)findViewById(R.id.dailyLogList);
        /*m_rvDailyLogs.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvDailyLogs.setLayoutManager(m_LayoutManager);


        m_Adapter = new NewAdapter(R.layout.dailylog_preview, 0, false, new DailyLogAdapterViewControl());

        m_rvDailyLogs.setAdapter(m_Adapter);*/

        m_Adapter = Tools.setupRecyclerView(m_rvDailyLogs, getApplicationContext(), R.layout.dailylog_preview, 0, false, new DailyLogAdapterViewControl());
        LoadDailyLogs();
    }

    private void LoadDailyLogs() {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/dailylog", null,
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
