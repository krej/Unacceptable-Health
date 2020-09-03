package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Models.CustomReturns.WorkoutPlanWithExtras;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class DebugTool extends AppCompatActivity {
    Button btnEncodeAllDailyLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_tool);
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

        btnEncodeAllDailyLogs = findViewById(R.id.btnEncodeAllDailyLogs);
        btnEncodeAllDailyLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EncodeAllDailyLogs();
            }
        });
    }

    private void EncodeAllDailyLogs() {
        IRepository repo = new Repository();
        repo.LoadAllDailyLogs(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {

                DailyLog[] logs = Tools.convertJsonResponseToObject(t, DailyLog[].class);

                for (DailyLog d : logs) {
                    boolean bChangesMade = false;
                    if (!Tools.IsEmptyString(d.MindfulMoment)) {
                        d.MindfulMoment = Tools.encodeToBase64(d.MindfulMoment);
                        bChangesMade = true;
                    }

                    if (!Tools.IsEmptyString(d.OverallNotes)) {
                        d.OverallNotes = Tools.encodeToBase64(d.OverallNotes);
                        bChangesMade = true;
                    }

                    if (bChangesMade) {
                        d.Save();
                    }
                }

                btnEncodeAllDailyLogs.setEnabled(false);
                Tools.ShowToast(getApplicationContext(), "Finshed!", Toast.LENGTH_LONG);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}