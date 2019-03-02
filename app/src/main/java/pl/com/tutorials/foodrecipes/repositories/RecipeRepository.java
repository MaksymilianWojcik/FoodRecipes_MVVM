package pl.com.tutorials.foodrecipes.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.requests.RecipeAPIClient;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeAPIClient recipeAPIClient;
    private String query;
    private int pageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>(); //its a medaitor if we want to make a change to a set of livedata before its returnede whenever its going to

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        recipeAPIClient = RecipeAPIClient.getInstance();
        initMediators();
    }

    public LiveData<List<Recipe>> getRecipes(){
//        return recipeAPIClient.getRecipes();
        return mRecipes; //we want to return medaitor cause we gonna make change to it before
    }

    public LiveData<Recipe > getRecipe(){
        return recipeAPIClient.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimedout(){
        return recipeAPIClient.isRecipeRequestTimedout();
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSourceCode = recipeAPIClient.getRecipes();
        mRecipes.addSource(recipeListApiSourceCode, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                //triggered when source changes
                //here we do smth before livedata is returned to activity
                if(recipes != null){
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                } else {
                    //search database cachce
                    doneQuery(null);
                }
            }
        });
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    public void doneQuery(List<Recipe> list){
        if(list != null){ //this means query is exhausted, 30 cause its the amount we get from rest api call
            if(list.size() < 30) {
                mIsQueryExhausted.setValue(true);
            }
        } else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public void searchRecipesAPI(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        this.query = query; //saving to access as previous state
        this.pageNumber = pageNumber; //saving to access as previous state
        mIsQueryExhausted.setValue(false); //as soon as we start query it is not exhausted yet
        recipeAPIClient.searchRecipesAPI(query, pageNumber);
    }

    public void searchRecipeById(String recipeId){
        recipeAPIClient.searchRecipeById(recipeId);
    }

    public void searchNextPage(){
        searchRecipesAPI(query, pageNumber + 1);
    }

    public void cancelRequesty(){
        recipeAPIClient.cancelRequest();
    }
}
