package pl.com.tutorials.foodrecipes.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.requests.RecipeAPIClient;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeAPIClient recipeAPIClient;
    private String query;
    private int pageNumber;

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        recipeAPIClient = RecipeAPIClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeAPIClient.getRecipes();
    }

    public LiveData<Recipe > getRecipe(){
        return recipeAPIClient.getRecipe();
    }


    public void searchRecipesAPI(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        query = query; //saving to access as previous state
        pageNumber = pageNumber; //saving to access as previous state
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
