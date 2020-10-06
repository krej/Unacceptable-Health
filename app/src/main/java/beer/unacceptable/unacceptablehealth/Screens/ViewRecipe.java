package beer.unacceptable.unacceptablehealth.Screens;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Adapters.IngredientAdditionAdapter;
import beer.unacceptable.unacceptablehealth.Models.FoodRecipe;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.IngredientAddition;
import beer.unacceptable.unacceptablehealth.R;

public class ViewRecipe extends AppCompatActivity {

    private RecyclerView m_rvIngredients;
    private IngredientAdditionAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;
    private FoodRecipe CurrentRecipe;

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
        Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/ingredient", null,
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
        CurrentRecipe = (FoodRecipe) bundle.getSerializable("recipe");

        if (CurrentRecipe != null) {
            //Tools.ShowToast(getApplicationContext(), r.name, Toast.LENGTH_LONG);
            //reload it
            Network.WebRequest(Request.Method.GET, Preferences.HealthAPIURL() + "/foodrecipe/" + CurrentRecipe.idString, null,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            CurrentRecipe = gson.fromJson(response, FoodRecipe.class);


                            ArrayList<IngredientAddition> myDataset = null;
                            String sName = "";
                            sName = CurrentRecipe.name;
                            myDataset = CurrentRecipe.ingredientAdditions;
                            m_Adapter.PopulateDataset(myDataset);
                            EditText instructions = findViewById(R.id.recipe_instructions);
                            instructions.setText(CurrentRecipe.instructions);
                            setTitle(sName);
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

        } else {
            String sName = "";
            sName = intent.getStringExtra("Name");
            CurrentRecipe = new FoodRecipe(sName);
            setTitle(sName);
        }

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

                CurrentRecipe.ClearIngredients();

                ArrayList<ListableObject> ingreds = m_Adapter.Dataset();
                for (int i = 0; i < ingreds.size(); i ++) {
                    CurrentRecipe.ingredientAdditions.add((IngredientAddition) ingreds.get(i));
                }
                CurrentRecipe.notes = "hardcoded notes";
                EditText instructions = findViewById(R.id.recipe_instructions);
                CurrentRecipe.instructions = instructions.getText().toString();

                CurrentRecipe.Save();
        }

        return true;
    }
}
