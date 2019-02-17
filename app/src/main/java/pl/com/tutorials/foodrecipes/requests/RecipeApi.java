package pl.com.tutorials.foodrecipes.requests;

import pl.com.tutorials.foodrecipes.requests.responses.RecipeResponse;
import pl.com.tutorials.foodrecipes.requests.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
            );

    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("key") String key,
            @Query("rId") String recipeId
    );
}
