package beer.unacceptable.unacceptablehealth.Activities;

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

import beer.unacceptable.unacceptablehealth.Adapters.MyAdapter;
import beer.unacceptable.unacceptablehealth.Models.Ingredient;
import beer.unacceptable.unacceptablehealth.Models.Recipe;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Tools.Tools;

public class ViewRecipe extends AppCompatActivity {

    private RecyclerView m_rvIngredients;
    private RecyclerView.Adapter m_Adapter;
    private RecyclerView.LayoutManager m_LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Recipe r = (Recipe) bundle.getSerializable("recipe");

        //Tools.ShowToast(getApplicationContext(), r.name, Toast.LENGTH_LONG);
        setTitle(r.name);
/*
        m_rvIngredients = (RecyclerView)findViewById(R.id.recipeView);
        m_rvIngredients.setHasFixedSize(false);

        m_LayoutManager = new LinearLayoutManager(this);
        m_rvIngredients.setLayoutManager(m_LayoutManager);

        Ingredient[] myDataset = r.ingredients;

        m_Adapter = new MyAdapter(myDataset);
        m_rvIngredients.setAdapter(m_Adapter);*/
    }
}
