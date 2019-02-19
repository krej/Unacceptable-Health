package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

import beer.unacceptable.unacceptablehealth.Adapters.IngredientAdapterViewControl;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.R;

public class FoodDatabase extends AppCompatActivity {

    private RecyclerView m_rvIngredients;
    //private IngredientAdapter m_Adapter = new IngredientAdapter(R.layout.default_list, R.layout.dialog_edit_ingredient);
    private NewAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                m_Adapter.showAddItemDialog(m_rvIngredients.getContext(), null);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: Figure this out. i had to switch activity_food_database to load content_recipe_list because when loading content_food_database it couldn't find my id...
        //TODO: They're the exact same xml file though so I should probably just do some renaming
        m_rvIngredients = (RecyclerView)findViewById(R.id.recipeView);
        m_Adapter = new NewAdapter(R.layout.default_list, R.layout.dialog_edit_ingredient, true, new IngredientAdapterViewControl());
        EditText instructions = findViewById(R.id.recipe_instructions);
        instructions.setVisibility(View.GONE);

        m_rvIngredients.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvIngredients.setLayoutManager(m_LayoutManager);

        m_rvIngredients.setAdapter(m_Adapter);

        LoadIngredients();

    }

    private void LoadIngredients() {
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/ingredient", null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Ingredient[] ingreds = gson.fromJson(response, Ingredient[].class);

                        for (Ingredient i : ingreds) {
                            m_Adapter.add(i);
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
                        Tools.ShowToast(getApplicationContext(), "Failed to load ingredients: " + errorMsg, Toast.LENGTH_LONG);
                    }
                });
    }

}
