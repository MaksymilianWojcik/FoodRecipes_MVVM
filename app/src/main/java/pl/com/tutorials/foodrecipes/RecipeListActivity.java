package pl.com.tutorials.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pl.com.tutorials.foodrecipes.adapters.OnRecipeListener;
import pl.com.tutorials.foodrecipes.adapters.RecipeRecyclerAdapter;
import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.util.Testing;
import pl.com.tutorials.foodrecipes.util.VerticalSpacingItemDecorator;
import pl.com.tutorials.foodrecipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;

    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter mAdapter;

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recyclerView = findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        initSearchView();

        if(!mRecipeListViewModel.isViewingRecipes()){
            //display search categories
            displaySearchCategories();
        }

        //associating our custom toolbar with the support action bar in the activity
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public void onBackPressed() {
        if(mRecipeListViewModel.onBackPressed()){
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu); //this will inflate the menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_categories: {
                displaySearchCategories();
            }
            case R.id.action_about: {
                //TODO: about dialog
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.i(TAG, "onChanged: ");
                if(recipes != null && mRecipeListViewModel.isViewingRecipes()) {
                    Testing.printRecipes(recipes, "recipesTest");
                    mRecipeListViewModel.setIsPerformingQuery(false); //query was complete
                    mAdapter.setmRecipes(recipes);
                }
            }
        });
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(verticalSpacingItemDecorator);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesAPI(s, 1);
                mSearchView.clearFocus(); //removing focus from searchview so it won't consume click anymore. This is improtant for back button work proipely with our logic to stop the request
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
        mSearchView.clearFocus(); //removing focus from searchview so it won't consume click anymore. This is improtant for back button work proipely with our logic to stop the request
    }
}
