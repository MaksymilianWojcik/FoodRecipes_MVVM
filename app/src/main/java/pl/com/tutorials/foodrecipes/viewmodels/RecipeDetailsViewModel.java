package pl.com.tutorials.foodrecipes.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.repositories.RecipeRepository;

public class RecipeDetailsViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId; //keepiong track of recipe id to handle "problem" with remembvered state
    private boolean mDidRetrieveRecipe;

    public RecipeDetailsViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
        mDidRetrieveRecipe = false;
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeRepository.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimedout(){
        return mRecipeRepository.isRecipeRequestTimedout();
    }

    public void searchRecipeById(String recipeId ){
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public boolean didRetrieveRecipe() {
        return mDidRetrieveRecipe;
    }

    public void setDidRetrieveRecipe(boolean mDidRetrieveRecipe) {
        this.mDidRetrieveRecipe = mDidRetrieveRecipe;
    }
}
