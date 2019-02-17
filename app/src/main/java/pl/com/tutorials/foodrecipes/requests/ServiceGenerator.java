package pl.com.tutorials.foodrecipes.requests;

import pl.com.tutorials.foodrecipes.util.AppValues;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(AppValues.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();
    private static RecipeApi recipeApi = retrofit.create(RecipeApi.class);

    public static RecipeApi getRecipeApi() {
        return recipeApi;
    }
}
