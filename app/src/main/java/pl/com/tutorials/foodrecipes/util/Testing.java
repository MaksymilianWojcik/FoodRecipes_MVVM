package pl.com.tutorials.foodrecipes.util;

import android.util.Log;

import java.util.List;

import pl.com.tutorials.foodrecipes.models.Recipe;

public class Testing {

    public static void printRecipes(List<Recipe> recipes, String tag){
        for (Recipe recipe : recipes) {
            Log.i(tag, "onChanged: recipe: " + recipe.getTitle());
        }
    }
}
