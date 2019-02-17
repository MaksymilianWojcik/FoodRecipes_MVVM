package pl.com.tutorials.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.requests.RecipeApi;
import pl.com.tutorials.foodrecipes.requests.ServiceGenerator;
import pl.com.tutorials.foodrecipes.requests.responses.RecipeResponse;
import pl.com.tutorials.foodrecipes.requests.responses.RecipeSearchResponse;
import pl.com.tutorials.foodrecipes.util.AppValues;
import pl.com.tutorials.foodrecipes.util.Testing;
import pl.com.tutorials.foodrecipes.viewmodels.RecipeListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {


    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        subscribeObservers();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });
    }

    private void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.i(TAG, "onChanged: ");
                if(recipes != null) {
                    Testing.printRecipes(recipes, "recipesTest");
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
}
