package pl.com.tutorials.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        showProgressBar(true);
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

    /***
     * Now the problem here is this, that viewmodel takes care of configuration changes etc.
     * So when we open once activity, and press back, and open it again (for example for other recipe) it will first show the previous one
     * cause state of this is stored in state thanks to the LiveData and ViewModel. Activity can be destroyed, but the ViewModel stays still
     * and onchanged will be called
     */
    private void subscribeObservers(){
        recipeDetailsViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if (recipe != null){ //when acitvity is recreated the old recipe will be retrieved, if it not the progress bar wont be hidden and the parent will not be unshown
//                    Log.d(TAG, "onChanged: recipe" + recipe.getTitle());
//                    for(String ingredient : recipe.getIngredients()){
//                        Log.d(TAG, "onChanged: ingredient: " + ingredient);
//                    }
                    if(recipeDetailsViewModel.getRecipeId().equals(recipe.getRecipeId())){
                        //we retrieved new recipe
                        setRecipeProperties(recipe);
                        recipeDetailsViewModel.setDidRetrieveRecipe(true);
                    }
                }
            }
        });

        recipeDetailsViewModel.isRecipeRequestTimedout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean && !recipeDetailsViewModel.didRetrieveRecipe()){
                    //timedout
                    Log.d(TAG, "onChanged: timedout");
                }
            }
        });
    }


    private void setRecipeProperties(Recipe recipe){
        if(recipe != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImageUrl())
                    .into(vRecipeImage);
            vRecipeTitle.setText(recipe.getTitle());
            vRecipeRank.setText(String.valueOf(Math.round(recipe.getSocialRank())));

            vRecipeIngredientsContainer.removeAllViews(); //removing ingredients that were previously there
            for(String ingredient : recipe.getIngredients()){
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                vRecipeIngredientsContainer.addView(textView);
            }
        }

        showParent();
        showProgressBar(false);
    }

    private void showParent(){
        vScrollView.setVisibility(View.VISIBLE);
    }
}
