package pl.com.tutorials.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
        initSearchView();

        if(!mRecipeListViewModel.isViewingRecipes()){
            //display search categories
            displaySearchCategories();
        }
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

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesAPI(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesAPI(category, 1);
    }

    private void displaySearchCategories(){
        mRecipeListViewModel.setIsViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }
}
