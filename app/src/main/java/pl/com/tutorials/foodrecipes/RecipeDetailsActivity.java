package pl.com.tutorials.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.viewmodels.RecipeDetailsViewModel;

public class RecipeDetailsActivity extends BaseActivity {

    private static final String TAG = "RecipeDetailsActivity";

    public static final String INTENT_EXTRA_RECIPE = "recipe";

    private AppCompatImageView vRecipeImage;
    private TextView vRecipeTitle;
    private TextView vRecipeRank;
    private LinearLayout vRecipeIngredientsContainer;
    private ScrollView vScrollView;

    private RecipeDetailsViewModel recipeDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        initViews();
        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        subscribeObservers();

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(INTENT_EXTRA_RECIPE)){
            Recipe recipe = getIntent().getParcelableExtra(INTENT_EXTRA_RECIPE);
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
            recipeDetailsViewModel.searchRecipeById(recipe.getRecipeId());
        }
    }

    private void initViews(){
        vRecipeImage = findViewById(R.id.recipe_image);
        vRecipeTitle = findViewById(R.id.recipe_title);
        vRecipeRank = findViewById(R.id.recipe_social_score);
        vRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
        vScrollView = findViewById(R.id.parent);
    }

    private void subscribeObservers(){
        recipeDetailsViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if (recipe != null){
                    Log.d(TAG, "onChanged: recipe" + recipe.getTitle());
                    for(String ingredient : recipe.getIngredients()){
                        Log.d(TAG, "onChanged: ingredient: " + ingredient);
                    }
                }
            }
        });
    }
}
