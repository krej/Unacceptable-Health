package beer.unacceptable.unacceptablehealth.Screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;
import java.util.Calendar;

import beer.unacceptable.unacceptablehealth.Adapters.PendingGoalItemAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Controllers.CreateGoalController;
import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.Models.PendingGoalItem;
import beer.unacceptable.unacceptablehealth.Models.WorkoutType;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;

public class CreateGoal
        extends BaseActivity
        implements CreateGoalController.View {

    TextView m_etStartDate;
    TextView m_etEndDate;
    TextView m_etBriefDescription;
    Switch m_swGoalType;
    FloatingActionButton m_fab;

    private RecyclerView m_rvGoalItems;
    private NewAdapter m_Adapter;

    private CreateGoalController m_oController;
    private PendingGoalItemAdapterViewControl m_oViewControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_fab = findViewById(R.id.fab);
        m_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingGoalItem gi = new PendingGoalItem();
                m_Adapter.add(gi);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FindUIElements();
        CreateClickListeners();
        CreateNameListener();
        CreateGoalTypeListener();

        m_oViewControl = new PendingGoalItemAdapterViewControl();
        m_Adapter = Tools.setupRecyclerView(m_rvGoalItems, getApplicationContext(), R.layout.list_goal_items, 0, false, m_oViewControl);
        m_oController = new CreateGoalController(this, new DateLogic(), new Repository());
        m_oController.loadWorkoutTypes();
    }

    private void CreateGoalTypeListener() {
        m_swGoalType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_oViewControl.setDayVisibility(isChecked);
                //setFabVisibility(isChecked);

                m_oController.setPendingGoalItemsFromAdapter(m_Adapter.Dataset());
                m_oController.changeGoalType(isChecked);
            }
        });
    }

    private void setFabVisibility(boolean isChecked) {
        if (isChecked)
            m_fab.hide();
        else
            m_fab.show();
    }

    private void CreateNameListener() {
        m_etBriefDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                m_oController.setName(s.toString());
            }
        });
    }

    private void CreateClickListeners() {
        final Calendar cal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener listenerStart = createDateSetListner(cal, CreateGoalController.DateType.Start);
        final DatePickerDialog.OnDateSetListener listenerEnd = createDateSetListner(cal, CreateGoalController.DateType.End);

        m_etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateGoal.this, listenerStart, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        m_etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateGoal.this, listenerEnd, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener createDateSetListner(final Calendar cal, final CreateGoalController.DateType dateType) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                m_oController.setDate(cal, dateType);
            }
        };

        return date;
    }

    private void FindUIElements() {
        m_etStartDate = findViewById(R.id.goal_start_date);
        m_etEndDate = findViewById(R.id.goal_end_date);
        m_rvGoalItems = findViewById(R.id.goal_items);
        m_etBriefDescription = findViewById(R.id.goal_name);
        m_swGoalType = findViewById(R.id.goal_create_based_on_week);
    }

    @Override
    public void setStartDateText(String sDate) {
        m_etStartDate.setText(sDate);
    }

    @Override
    public void setEndDateText(String sDate) {
        m_etEndDate.setText(sDate);
    }

    @Override
    public void setGoalItems(ArrayList<PendingGoalItem> goalItems) {
        clearGoalItems();
        for(PendingGoalItem p : goalItems) {
            m_Adapter.add(p);
        }
        m_Adapter.notifyDataSetChanged();
    }

    @Override
    public void clearGoalItems() {
        m_Adapter.clear();
    }

    @Override
    public void sendWorkoutTypesToAdapter(WorkoutType[] types) {
        m_oViewControl.setWorkoutTypes(types);
    }
}
