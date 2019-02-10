package beer.unacceptable.unacceptablehealth.Screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;

public class ViewDailyLog extends AppCompatActivity {
    RadioGroup radFlo;
    /*TextView txtRateWork;
    RatingBar rWorkRating;*/
    LinearLayout llWorkRating;
    int animTime;
    DailyLog m_dlLog = null;

    static String DateFormat = "MM/dd/yyyy";

    DateFormat m_dtFormat = new SimpleDateFormat(DateFormat, Locale.ENGLISH);

    EditText etDate;
    RatingBar rbHealth;
    CheckBox chkBBD;
    CheckBox chkUsedFlonase;
    RadioButton radFlonaseBadAllergy;
    RadioButton radFlonaseStartingAllergies;
    RadioButton radFlonaseAllergyInsurance;
    CheckBox chkHadHeadache;
    CheckBox chkWorkDay;
    RatingBar rbWorkRating;
    RatingBar rbPersonalDayRating;
    TextView txtMindfulMoment;
    TextView txtOverallNotes;
    LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_log);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radFlo = findViewById(R.id.radGrp_flonase);
        /*txtRateWork = findViewById(R.id.txt_rate_work);
        rWorkRating = findViewById(R.id.rating_work);*/
        llWorkRating = findViewById(R.id.ll_work_rating);
        animTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

/*        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FindUIElements();

        SetupVisibilityClickListeners();


        PopulateDateField();
        Intent intent = this.getIntent();
        String idString = intent.getStringExtra("idString");


        if ( Tools.IsEmptyString(idString)) {
            setTitle("Create Daily Log");
            m_dlLog = new DailyLog();
        } else {
            LoadDailyLog(idString);
        }

        //llMain.fix
    }

    private void FindUIElements() {
        etDate = findViewById(R.id.daily_log_date);
        rbHealth = findViewById(R.id.rating_healthy);
        chkBBD = findViewById(R.id.chk_bbd);
        chkUsedFlonase = findViewById(R.id.chk_used_flonase);
        radFlonaseBadAllergy = findViewById(R.id.rad_flonase_bad_allergies);
        radFlonaseStartingAllergies = findViewById(R.id.rad_flonase_starting_allergies);
        radFlonaseAllergyInsurance = findViewById(R.id.rad_flonase_allergy_insurance);
        chkHadHeadache = findViewById(R.id.chk_had_headache);
        chkWorkDay = findViewById(R.id.chk_work_day);
        rbWorkRating = findViewById(R.id.rating_work);
        rbPersonalDayRating = findViewById(R.id.rating_day);
        txtMindfulMoment = findViewById(R.id.txt_mindful_moment);
        txtOverallNotes = findViewById(R.id.txt_overall_notes);
        llMain = findViewById(R.id.ll_daily_log_main);
    }

    private void LoadDailyLog(String idString) {
        Network.WebRequest(Request.Method.GET, Tools.HealthAPIURL() + "/dailylog/" + idString, null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        DailyLog dailyLog = gson.fromJson(response, DailyLog.class);
                        m_dlLog = dailyLog;
                        setTitle("Daily Log: " + Tools.FormatDate(m_dlLog.date, "MMM dd yy"));
                        FillScreen();
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

    private void FillScreen() {
        etDate.setText(Tools.FormatDate(m_dlLog.date, DateFormat));
        rbHealth.setRating(m_dlLog.HealthRating);
        chkBBD.setChecked(m_dlLog.BBD);
        chkUsedFlonase.setChecked(m_dlLog.UsedFlonase);
        switch (m_dlLog.FlonaseReasoning) {
            case 1:
                radFlonaseBadAllergy.setChecked(true);
                break;
            case 2:
                radFlonaseStartingAllergies.setChecked(true);
                break;
            case 3:
                radFlonaseAllergyInsurance.setChecked(true);
                break;
        }
        chkHadHeadache.setChecked(m_dlLog.HadHeadache);
        chkWorkDay.setChecked(m_dlLog.WorkDay);
        rbWorkRating.setRating(m_dlLog.WorkRating);
        rbPersonalDayRating.setRating(m_dlLog.PersonalDayRating);
        txtMindfulMoment.setText(m_dlLog.MindfulMoment);
        txtOverallNotes.setText(m_dlLog.OverallNotes);

        AnimateView(chkUsedFlonase, radFlo);
        AnimateView(chkWorkDay, llWorkRating);
    }

    private void SetupVisibilityClickListeners() {
        final CheckBox chkFlonase = findViewById(R.id.chk_used_flonase);
        final CheckBox chkWorkDay = findViewById(R.id.chk_work_day);

        CreateVisibilityOnClickListener(chkFlonase, radFlo);
        CreateVisibilityOnClickListener(chkWorkDay, llWorkRating);

    }

    private void CreateVisibilityOnClickListener(final CheckBox chkCondition, final View vToAnimate) {
        chkCondition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AnimateView(chkCondition, vToAnimate);
            }

        });
    }

    private void AnimateView(CheckBox chkCondition, View vToAnimate) {
        if (chkCondition.isChecked()) {
            AnimateShowView(vToAnimate);
            /*radFlo.animate()
                    .translationY(radFlo.getHeight())
                    .alpha(1f)
                    .setListener(null);*/
        } else {
            AnimateHideView(vToAnimate);
        }
    }

    private void AnimateHideView(final View vToAnimate) {
        vToAnimate.animate()
                .translationY(0)
                .setDuration(animTime)
                .alpha(0f)
                .setListener(
                        new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        vToAnimate.setVisibility(View.GONE);
                    }
                });
    }

    private void AnimateShowView(View vToAnimate) {
        vToAnimate.setVisibility(View.VISIBLE);
        vToAnimate.setAlpha(0f);
        vToAnimate.animate()
                .alpha(1f)
                .setDuration(animTime)
                .setListener(null);
    }

    private void PopulateDateField() {
        Date date = new Date();
        EditText dateView = findViewById(R.id.daily_log_date);
        dateView.setText(m_dtFormat.format(date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_recipe:
                PopulateDailyLog();
                m_dlLog.Save();
                //m_dlLog.Clear();

                /*ArrayList<ListableObject> ingreds = m_Adapter.Dataset();
                for (int i = 0; i < ingreds.size(); i ++) {
                    CurrentRecipe.ingredientAdditions.add((IngredientAddition) ingreds.get(i));
                }
                CurrentRecipe.notes = "hardcoded notes";
                EditText instructions = findViewById(R.id.recipe_instructions);
                CurrentRecipe.instructions = instructions.getText().toString();

                CurrentRecipe.Save();*/
        }

        return true;
    }

    private void PopulateDailyLog() {

        m_dlLog.name = etDate.getText().toString();
        try {
            m_dlLog.date = m_dtFormat.parse(etDate.getText().toString());
        } catch ( ParseException ex) {
            if (m_dlLog.date == null)
                m_dlLog.date = new Date();
        }

        m_dlLog.date = dateAtNoon(m_dlLog.date);

        m_dlLog.HealthRating = (int)rbHealth.getRating();
        m_dlLog.BBD = chkBBD.isChecked();
        m_dlLog.UsedFlonase = chkUsedFlonase.isChecked();
        m_dlLog.FlonaseReasoning = GetFlonaseReasoning(chkUsedFlonase, radFlonaseBadAllergy, radFlonaseStartingAllergies, radFlonaseAllergyInsurance);
        m_dlLog.HadHeadache = chkHadHeadache.isChecked();
        m_dlLog.WorkDay = chkWorkDay.isChecked();
        if (m_dlLog.WorkDay) {
            m_dlLog.WorkRating = (int) rbWorkRating.getRating();
        } else {
            m_dlLog.WorkRating = 0;
        }
        m_dlLog.PersonalDayRating = (int)rbPersonalDayRating.getRating();
        m_dlLog.MindfulMoment = txtMindfulMoment.getText().toString();
        m_dlLog.OverallNotes = txtOverallNotes.getText().toString();
    }

    private Date dateAtNoon(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.setTimeZone(TimeZone.getTimeZone("CDT"));


        return cal.getTime();
    }

    private int GetFlonaseReasoning(CheckBox chkUsedFlonase, RadioButton radFlonaseBadAllergy, RadioButton radFlonaseStartingAllergies, RadioButton radFlonaseAllergyInsurance) {
        if ( !chkUsedFlonase.isChecked() ) return 0;
        if (radFlonaseBadAllergy.isChecked()) return 1;
        if (radFlonaseStartingAllergies.isChecked()) return 2;
        if (radFlonaseAllergyInsurance.isChecked()) return 3;
        return 0;
    }
}
