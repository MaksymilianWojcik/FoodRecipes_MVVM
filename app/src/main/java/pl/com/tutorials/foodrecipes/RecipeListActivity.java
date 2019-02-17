package pl.com.tutorials.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import pl.com.tutorials.foodrecipes.adapters.OnRecipeListener;
import pl.com.tutorials.foodrecipes.adapters.RecipeRecyclerAdapter;
import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.util.Testing;
import pl.com.tutorials.foodrecipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {


    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;

    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recyclerView = findViewById(R.id.recipe_list);
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        testRetrofitRequest();
    }

    private void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.i(TAG, "onChanged: ");
                if(recipes != null) {
                    Testing.printRecipes(recipes, "recipesTest");
                    mAdapter.setmRecipes(recipes);
                }
            }
        });
    }

    public void searchRecipesAPI(String query, int pageNumber) {
        mRecipeListViewModel.searchRecipesAPI(query, pageNumber);
    }

    private void testRetrofitRequest(){
        searchRecipesAPI("chicken breast", 1);
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
