package pl.com.tutorials.foodrecipes.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.repositories.RecipeRepository;

public class RecipeDetailsViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;

    public RecipeDetailsViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeRepository.getRecipe();
    }

    public void searchRecipeById(String recipeId ){
        mRecipeRepository.searchRecipeById(recipeId);
    }
}
