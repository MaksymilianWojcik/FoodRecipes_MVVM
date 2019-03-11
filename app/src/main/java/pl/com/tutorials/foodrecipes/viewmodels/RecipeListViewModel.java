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
    private boolean mIsPerformingQuery;

    public RecipeListViewModel() {
        this.mRecipeRepository = RecipeRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();

    }
    public LiveData<Boolean> isQueryExhausted(){
        return mRecipeRepository.isQueryExhausted();
    }

    public void searchRecipesAPI(String query, int pageNumber){
        mIsViewingRecipes = true;
        mIsPerformingQuery = true;
        mRecipeRepository.searchRecipesAPI(query, pageNumber);
    }

    public void searchNextPage(){
        if(!mIsPerformingQuery && mIsViewingRecipes
                && !isQueryExhausted().getValue()){ //if we try to search for next page, but query is exhausted, we dont want to do that
            mRecipeRepository.searchNextPage();
        }
    }

    public boolean isViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setIsViewingRecipes(boolean mIsViewingRecipes) {
        this.mIsViewingRecipes = mIsViewingRecipes;
    }

    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean mIsPerformingQuery) {
        this.mIsPerformingQuery = mIsPerformingQuery;
    }

    public boolean onBackPressed(){
        if(mIsPerformingQuery){
            //cancel retrofit query -> reporisotry -> client
            mRecipeRepository.cancelRequesty();
            mIsPerformingQuery = false;
        }
        if(mIsViewingRecipes){
            mIsViewingRecipes = false;
            return false;
        }
        return true;
    }
}
