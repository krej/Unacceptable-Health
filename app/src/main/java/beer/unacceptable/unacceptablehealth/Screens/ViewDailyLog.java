package beer.unacceptable.unacceptablehealth.Screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.DailyLog;
import beer.unacceptable.unacceptablehealth.R;

public class ViewDailyLog extends AppCompatActivity {
    RadioGroup radFlo;
    /*TextView txtRateWork;
    RatingBar rWorkRating;*/
    LinearLayout llWorkRating;
    int animTime;
    DailyLog m_dlLog = null;

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

        SetupVisibilityClickListeners();


        PopulateDateField();

        if ( m_dlLog == null) {
            setTitle("Create Daily Log");
        }
    }

    private void SetupVisibilityClickListeners() {
        final CheckBox chkFlonase = findViewById(R.id.chk_used_flonase);
        final CheckBox chkWorkDay = findViewById(R.id.chk_work_day);

        AnimateViews(chkFlonase, radFlo);
        AnimateViews(chkWorkDay, llWorkRating);

    }

    private void AnimateViews(final CheckBox chkCondition, final View vToAnimate) {
        chkCondition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (chkCondition.isChecked()) {
                    vToAnimate.setVisibility(View.VISIBLE);
                    vToAnimate.setAlpha(0f);
                    vToAnimate.animate()
                            .alpha(1f)
                            .setDuration(animTime)
                            .setListener(null);
                    /*radFlo.animate()
                            .translationY(radFlo.getHeight())
                            .alpha(1f)
                            .setListener(null);*/
                } else {
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
            }
        });
    }

    private void PopulateDateField() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        EditText dateView = findViewById(R.id.daily_log_date);
        dateView.setText(dateFormat.format(date));
    }

}
