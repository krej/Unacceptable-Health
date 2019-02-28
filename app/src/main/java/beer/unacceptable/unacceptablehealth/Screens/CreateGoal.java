package beer.unacceptable.unacceptablehealth.Screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Adapters.NewAdapter;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
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
    TextView m_tvName;
    TextView m_etBriefDescription;
    Switch m_swGoalType;
    Spinner m_spGoalAmountType;
    EditText m_etGoalAmount;
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
        m_oController = new CreateGoalController(this, new DateLogic(), new Repository(), new LibraryRepository());
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
                DatePickerDialog dialogDatePicker = new DatePickerDialog(CreateGoal.this, listenerStart, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialogDatePicker.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialogDatePicker.show();
            }
        });

        m_etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogDatePicker = new DatePickerDialog(CreateGoal.this, listenerEnd, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialogDatePicker.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialogDatePicker.show();
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
        m_etBriefDescription = findViewById(R.id.goal_description);
        m_swGoalType = findViewById(R.id.goal_create_based_on_week);
        m_tvName = findViewById(R.id.goal_name);
        m_spGoalAmountType = findViewById(R.id.goal_amount_type);
        m_etGoalAmount = findViewById(R.id.goal_amount);
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
        ArrayAdapter<WorkoutType> aa = new ArrayAdapter<>(m_spGoalAmountType.getContext(), android.R.layout.simple_spinner_dropdown_item, types);
        m_spGoalAmountType.setAdapter(aa);
    }

    @Override
    public void showNameError() {
        m_tvName.setError(getString(R.string.error_field_required));
    }

    @Override
    public void showDescriptionError() {
        m_etBriefDescription.setError(getString(R.string.error_field_required));
    }

    @Override
    public void showStartDateError() {
        m_etStartDate.setError(getString(R.string.error_field_required));
    }

    @Override
    public void showEndDateError(String sMessage) {
        m_etEndDate.setError(sMessage);
    }

    @Override
    public void showMessage(String sMessage) {
        Tools.ShowToast(getApplicationContext(), sMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void clearErrors() {
        //TODO: I think i'm gonna want to move these into their own functions so that I can have it clear errors instantly when they're fixed
        m_etStartDate.setError(null);
        m_etEndDate.setError(null);
        m_tvName.setError(null);
        m_etBriefDescription.setError(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_recipe, menu);
        MenuItem miSave = menu.findItem(R.id.save_recipe);
        //miSave.setVisible(m_oLogic == null || m_oLogic.canEditLog());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_recipe:
                SaveGoal();
        }

        return true;
    }

    private void SaveGoal() {
        String sName = m_tvName.getText().toString();
        String sDescription = m_etBriefDescription.getText().toString();
        boolean bBasedOnWeek = m_swGoalType.isChecked();
        double dGoalAmount = Tools.ParseDouble(m_etGoalAmount.getText().toString());
        WorkoutType wtGoalType = (WorkoutType)m_spGoalAmountType.getSelectedItem();
        //m_oController.setPendingGoalItemsFromAdapter(m_Adapter.Dataset());

        m_oController.saveGoal(sName, sDescription, bBasedOnWeek, m_Adapter.Dataset(), dGoalAmount, wtGoalType);
    }
}
