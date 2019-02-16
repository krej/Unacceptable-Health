package beer.unacceptable.unacceptablehealth.Screens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.Adapters.RecipeAdapter;
import beer.unacceptable.unacceptablehealth.Models.FoodRecipe;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.IngredientAddition;
import beer.unacceptable.unacceptablehealth.R;

public class RecipeList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView m_rvRecipes;
    private RecipeAdapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                m_Adapter.AddItem(m_rvRecipes.getContext(), null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //my code
        m_rvRecipes = (RecyclerView)findViewById(R.id.recipeView);
        m_rvRecipes.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvRecipes.setLayoutManager(m_LayoutManager);


        m_Adapter = new RecipeAdapter(R.layout.recipe_list_preview, R.layout.dialog_name);
        LoadRecipes();

        m_rvRecipes.setAdapter(m_Adapter);

        //TODO: content_recipe_list.xml is shared between this screen (RecipeList) and ViewRecipe because its really just a generic list...
        //TODO: I don't like how this field is shared between both and then hidden here, but it was kinda a pain to fix it right now and its late and I want to be working on something else...
        EditText instructions = findViewById(R.id.recipe_instructions);
        instructions.setVisibility(View.GONE);
    }

    private void LoadRecipes() {
        /*ArrayList<IngredientAddition> i1 = new ArrayList<IngredientAddition> ();
        i1.add(new IngredientAddition(5, "grams", new Ingredient("Flour")));

        ArrayList<IngredientAddition> i2 = new ArrayList<IngredientAddition>();
        i2.add(new IngredientAddition(1, "packet", new Ingredient("Muffin Mix")));
        i2.add(new IngredientAddition(50, "grams", new Ingredient("Water")));

        ArrayList<IngredientAddition> i3 = new ArrayList<IngredientAddition>();
        i3.add(new IngredientAddition(2, "eggs", new Ingredient("Egg Wash")));
        i3.add(new IngredientAddition(70, "grams", new Ingredient("Corn Meal")));
        i3.add(new IngredientAddition(99, "grams", new Ingredient("Water")));

        FoodRecipe r1 = new FoodRecipe("Basic Muffins", i1);
        FoodRecipe r2 = new FoodRecipe("Insane Muffins", i2);
        FoodRecipe r3 = new FoodRecipe("Out of this world Muffins", i3);

        m_Adapter.add(r1);

        m_Adapter.add(r2);
        m_Adapter.add(r3);*/

        //TODO: This shouldn't load ENTIRE recipes, just the names because we're gonna need to reload it later...
        Network.WebRequest(Request.Method.GET, Preferences.RestAPIURL() + "/foodrecipe", null,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        FoodRecipe[] recipes = gson.fromJson(response, FoodRecipe[].class);

                        for (FoodRecipe fr : recipes) {
                            m_Adapter.add(fr);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
