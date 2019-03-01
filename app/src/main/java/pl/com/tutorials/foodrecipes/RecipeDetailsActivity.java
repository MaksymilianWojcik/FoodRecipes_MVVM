package pl.com.tutorials.foodrecipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import pl.com.tutorials.foodrecipes.models.Recipe;

public class RecipeDetailsActivity extends BaseActivity {

    public static final String INTENT_EXTRA_RECIPE = "recipe";

    private AppCompatImageView vRecipeImage;
    private TextView vRecipeTitle;
    private TextView vRecipeRank;
    private LinearLayout vRecipeIngredientsContainer;
    private ScrollView vScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(INTENT_EXTRA_RECIPE)){
            Recipe recipe = getIntent().getParcelableExtra(INTENT_EXTRA_RECIPE);
        }
    }

    private void initViews(){
        vRecipeImage = findViewById(R.id.recipe_image);
        vRecipeTitle = findViewById(R.id.recipe_title);
        vRecipeRank = findViewById(R.id.recipe_social_score);
        vRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
        vScrollView = findViewById(R.id.parent);
    }
}
