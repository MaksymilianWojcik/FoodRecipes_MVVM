package pl.com.tutorials.foodrecipes.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pl.com.tutorials.foodrecipes.AppExecutor;
import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.requests.responses.RecipeSearchResponse;
import pl.com.tutorials.foodrecipes.util.AppValues;
import retrofit2.Call;
import retrofit2.Response;

public class RecipeAPIClient {

    private static final String TAG = "RecipeAPIClient";
    private static RecipeAPIClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private RetrieveRecipesRunnable retrieveRecipesRunnable;


    public static RecipeAPIClient getInstance() {
        if(instance == null){
            instance = new RecipeAPIClient();
        }
        return instance;
    }

    private RecipeAPIClient(){
        mRecipes = new MutableLiveData<List<Recipe>>();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public void searchRecipesAPI(String query, int pageNumber){
        if(retrieveRecipesRunnable != null){
            retrieveRecipesRunnable = null;
        }
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
        final Future handler = AppExecutor.getInstance().networkIO().submit(retrieveRecipesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //run after a specified time
                //let the user know its timed out
                handler.cancel(true); //interrupting the backgrounthread from making request if timeout is over
            }
        }, AppValues.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void cancelRequest(){
        if(retrieveRecipesRunnable != null) {
            retrieveRecipesRunnable.cancelRequest();
        }
    }

    private class RetrieveRecipesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute(); //this will be executed o nthe backgorund thread, because this is accessing the netowrk request
                if(cancelRequest){ return; }
                if(response.code() == 200){
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if(pageNumber == 1){
                        mRecipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.i(TAG, "run: " + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber){
            return ServiceGenerator.getRecipeApi().searchRecipe(AppValues.API_KEY, query, String.valueOf(pageNumber));
        }

        private void cancelRequest(){
            Log.i(TAG, "cancelRequest: Cancelling search request");
            cancelRequest = true;
        }
    }


}
