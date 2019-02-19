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

import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.Logic.DailyLogLogic;
import beer.unacceptable.unacceptablehealth.Logic.DateLogic;
import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class ViewDailyLog extends AppCompatActivity implements DailyLogLogic.View {
    RadioGroup radFlo;
    LinearLayout llWorkRating;
    int animTime;

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

    private DailyLogLogic m_oLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_log);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_oLogic = new DailyLogLogic(new Repository(), new DateLogic(), new LibraryRepository());
        m_oLogic.attachView(this);

        FindUIElements();
        SetupVisibilityClickListeners();

        Intent intent = this.getIntent();
        String idString = intent.getStringExtra("idString");

        //attempt again to stop the random crashes by making sure the preferences are loaded
        Preferences.getInstance(this, "health");

        m_oLogic.LoadLog(idString);
    }

    public void setScreenTitle(String sTitle) {
        setTitle(sTitle);
    }

    public void showMessage(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }

    public void setScreenControlsEnabled(boolean bEnabled) {
        etDate.setEnabled(bEnabled);
        rbHealth.setEnabled(bEnabled);
        chkBBD.setEnabled(bEnabled);
        chkUsedFlonase.setEnabled(bEnabled);
        radFlonaseBadAllergy.setEnabled(bEnabled);
        radFlonaseStartingAllergies.setEnabled(bEnabled);
        radFlonaseAllergyInsurance.setEnabled(bEnabled);
        chkHadHeadache.setEnabled(bEnabled);
        chkWorkDay.setEnabled(bEnabled);
        rbWorkRating.setEnabled(bEnabled);
        rbPersonalDayRating.setEnabled(bEnabled);
        txtMindfulMoment.setEnabled(bEnabled);
        txtOverallNotes.setEnabled(bEnabled);
        llMain.setEnabled(bEnabled);

        invalidateOptionsMenu();
    }

    private void FindUIElements() {
        //main fields i save
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

        //other stuff
        radFlo = findViewById(R.id.radGrp_flonase);
        llWorkRating = findViewById(R.id.ll_work_rating);
        animTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public void fillScreen(DailyLog dailyLog) {
        etDate.setText(dailyLog.formatDate());
        rbHealth.setRating(dailyLog.HealthRating);
        chkBBD.setChecked(dailyLog.BBD);
        chkUsedFlonase.setChecked(dailyLog.UsedFlonase);
        radFlonaseBadAllergy.setChecked(dailyLog.isFlonaseBadAllergy());
        radFlonaseStartingAllergies.setChecked(dailyLog.isFlonaseStartingAllergies());
        radFlonaseAllergyInsurance.setChecked(dailyLog.isFlonaseAllergyInsurance());
        chkHadHeadache.setChecked(dailyLog.HadHeadache);
        chkWorkDay.setChecked(dailyLog.WorkDay);
        rbWorkRating.setRating(dailyLog.WorkRating);
        rbPersonalDayRating.setRating(dailyLog.PersonalDayRating);
        txtMindfulMoment.setText(dailyLog.MindfulMoment);
        txtOverallNotes.setText(dailyLog.OverallNotes);

        AnimateView(chkUsedFlonase, radFlo);
        AnimateView(chkWorkDay, llWorkRating);

    }

    private void SetupVisibilityClickListeners() {
        CreateVisibilityOnClickListener(chkUsedFlonase, radFlo);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_recipe, menu);
        MenuItem miSave = menu.findItem(R.id.save_recipe);
        miSave.setVisible(m_oLogic == null || m_oLogic.canEditLog());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_recipe:
                SaveDailyLog();
        }

        return true;
    }

    private void SaveDailyLog() {

        String sDate = etDate.getText().toString();
        int iHealthRating = (int)rbHealth.getRating();
        boolean bBBD = chkBBD.isChecked();
        boolean bUsedFlonase = chkUsedFlonase.isChecked();
        boolean bBadAllergy = radFlonaseBadAllergy.isChecked();
        boolean bStartingAllergies = radFlonaseStartingAllergies.isChecked();
        boolean bAllergyInsurance = radFlonaseAllergyInsurance.isChecked();
        boolean bHadHeadache = chkHadHeadache.isChecked();
        boolean bWorkDay = chkWorkDay.isChecked();
        int iWorkRating = (int)rbWorkRating.getRating();
        int iPersonalDayRating = (int)rbPersonalDayRating.getRating();
        String sMindfulMoment = txtMindfulMoment.getText().toString();
        String sOverallNotes = txtOverallNotes.getText().toString();

        m_oLogic.saveLog(sDate,
                iHealthRating,
                bBBD,
                bUsedFlonase,
                bBadAllergy,
                bStartingAllergies,
                bAllergyInsurance,
                bHadHeadache,
                bWorkDay,
                iWorkRating,
                iPersonalDayRating,
                sMindfulMoment,
                sOverallNotes);
    }

}
