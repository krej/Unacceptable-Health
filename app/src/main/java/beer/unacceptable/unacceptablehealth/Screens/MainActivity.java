package beer.unacceptable.unacceptablehealth.Screens;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.CustomizedExceptionHandler;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Controllers.DailyLogLogic;
import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Controllers.MainScreenController;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Receivers.DailyLogAlarmReceiver;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainScreenController.View {

    CardView m_cvTodaysLog;
    Button m_bNewLog;
    TextView m_tvDailyLogHeader;
    TextView m_tvIdString;
    RatingBar m_rbOverallRating;
    DailyLogLogic m_oDailyLogController;
    MainScreenController m_oController;
    LinearLayout m_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_oController = new MainScreenController(new Repository(), new DateLogic());
        m_oController.attachView(this);
        m_oDailyLogController = new DailyLogLogic(new Repository(), new DateLogic(), new LibraryRepository());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FindUIElements();
        SetupOnClickListeners();

        m_oController.LoadTodaysLog();;
    }

    private void SetupOnClickListeners() {
        m_bNewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewDailyLog.class);
                startActivity(i);
            }
        });

        m_cvTodaysLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = m_tvIdString.getText().toString();
                Intent intent = new Intent(v.getContext(), ViewDailyLog.class);
                intent.putExtra("idString", idString);

                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity)v.getContext(),

                        // Now we provide a list of Pair items which contain the view we can transitioning
                        // from, and the name of the view it is transitioning to, in the launched activity
                        new Pair<View, String>(v.findViewById(R.id.todays_log_date),
                                ViewDailyLog.VIEW_NAME_HEADER_TITLE));

                //v.getContext().startActivity(intent);
                ActivityCompat.startActivity(v.getContext(), intent, activityOptions.toBundle());
            }
        });
    }

    private void FindUIElements() {
        m_bNewLog = findViewById(R.id.create_daily_log);
        m_cvTodaysLog = findViewById(R.id.todays_log_card_view);
        m_rbOverallRating = findViewById(R.id.dailyLogPreview_OverallDayRating);
        m_tvDailyLogHeader = findViewById(R.id.todays_log_date);
        m_tvIdString = findViewById(R.id.idString);
        m_ll = findViewById(R.id.linear_layout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Class<?> classToLaunch = null;

        switch(id) {
            case R.id.action_settings:
                classToLaunch = Settings.class;
                break;
            case R.id.create_daily_log:
                classToLaunch = ViewDailyLog.class;
                break;
        }

        if (classToLaunch != null) {
            Intent i = new Intent(getApplicationContext(), classToLaunch);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class<?> classToLaunch = null;


        switch (id) {
            case R.id.nav_exercise:
                break;
            case R.id.nav_nutrition:
                break;
            case R.id.nav_recipes:
                classToLaunch = RecipeList.class;
                break;
            case R.id.nav_daily_logs:
                classToLaunch = DailyLogList.class;
                break;
            case R.id.nav_goals:
                classToLaunch = CreateGoal.class;
                break;
            case R.id.nav_food_database:
                classToLaunch = FoodDatabase.class;
                break;
            case R.id.nav_sign_out:
                Tools.LaunchSignInScreen(this, MainActivity.class);
                break;
            case R.id.nav_exercise_database:
                classToLaunch = ExerciseDatabase.class;
                break;
        }

        if (classToLaunch != null) {
            Intent intent = new Intent(getApplicationContext(), classToLaunch);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showTodaysLog(boolean b) {
        if (b) {
            m_cvTodaysLog.setVisibility(View.VISIBLE);
        } else {
            m_cvTodaysLog.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNewLogButton(boolean b) {
        if (b) {
            m_bNewLog.setVisibility(View.VISIBLE);
        } else {
            m_bNewLog.setVisibility(View.GONE);
        }
    }

    @Override
    public void populateTodaysLog(DailyLog dl) {
        m_tvDailyLogHeader.setText(m_oDailyLogController.getLongDate(dl));
        m_rbOverallRating.setRating(m_oDailyLogController.getDaysAverageRating(dl));
        m_tvIdString.setText(dl.idString);
    }

    @Override
    public void showDailyLogError() {
        TextView tvError = new TextView(getApplicationContext());
        tvError.setText(getString(R.string.error_loading_dailylog));
        m_ll.addView(tvError);
    }
}
