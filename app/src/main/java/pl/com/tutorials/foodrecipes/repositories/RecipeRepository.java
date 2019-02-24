package pl.com.tutorials.foodrecipes.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.requests.RecipeAPIClient;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeAPIClient recipeAPIClient;

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

    public void searchRecipesAPI(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        recipeAPIClient.searchRecipesAPI(query, pageNumber);
    }

    public void cancelRequesty(){
        recipeAPIClient.cancelRequest();
    }
}
