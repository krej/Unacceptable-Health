package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
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

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Adapters.IngredientAdditionAdapter;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.IngredientAddition;
import beer.unacceptable.unacceptablehealth.Models.Recipe;
import beer.unacceptable.unacceptablehealth.R;

public class ViewRecipe extends AppCompatActivity {

    private RecyclerView m_rvIngredients;
    private IngredientAdditionAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                m_Adapter.AddItem(m_rvIngredients.getContext(), null);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        m_rvIngredients = (RecyclerView)findViewById(R.id.recipeView);
        m_rvIngredients.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvIngredients.setLayoutManager(m_LayoutManager);


        m_Adapter = new IngredientAdditionAdapter(R.layout.content_list_ingredients, R.layout.ingredient_selection);
        m_rvIngredients.setAdapter(m_Adapter);

        LoadRecipe();
        LoadIngredients();
    }

    private void LoadIngredients() {
        Network.WebRequest(Request.Method.GET, Tools.RestAPIURL() + "/ingredient", null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Ingredient[] ingreds = gson.fromJson(response, Ingredient[].class);

                        m_Adapter.PopulateIngredients(ingreds);
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

    private void LoadRecipe() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Recipe r = (Recipe) bundle.getSerializable("recipe");
        String sName = "";
        ArrayList<IngredientAddition> myDataset = null;

        if (r != null) {
            //Tools.ShowToast(getApplicationContext(), r.name, Toast.LENGTH_LONG);
            sName = r.name;
            myDataset = r.ingredientAdditions;
            m_Adapter.PopulateDataset(myDataset);
        } else {
            sName = intent.getStringExtra("Name");
        }

        setTitle(sName);
    }
}
