package pl.com.tutorials.foodrecipes.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pl.com.tutorials.foodrecipes.models.Recipe;

public class RecipeResponse {

    @SerializedName("recipe")
    @Expose() //so gson converter can deserialize and serialize data coming from web response
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
