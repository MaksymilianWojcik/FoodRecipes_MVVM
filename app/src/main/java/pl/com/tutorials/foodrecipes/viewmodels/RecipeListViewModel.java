package pl.com.tutorials.foodrecipes.viewmodels;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.repositories.RecipeRepository;

//ViewModel i AndroidViewModel are almost the same, but AndroidViewModel constructor matching super has application as an arg.
//So if we need to use Application, we should use AndroidViewModel
public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;

    public RecipeListViewModel() {
        this.mIsViewingRecipes = false;
        this.mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesAPI(String query, int pageNumber){
        mIsViewingRecipes = true;
        if (pageNumber == 0){
            pageNumber = 1;
        }
        mRecipeRepository.searchRecipesAPI(query, pageNumber);
    }

    public boolean isViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setIsViewingRecipes(boolean mIsViewingRecipes) {
        this.mIsViewingRecipes = mIsViewingRecipes;
    }
}
