package pl.com.tutorials.foodrecipes.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import pl.com.tutorials.foodrecipes.R;
import pl.com.tutorials.foodrecipes.models.Recipe;
import pl.com.tutorials.foodrecipes.util.AppValues;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    public static final int CATEGORY_TYPE = 3;
    public static final int EXHAUSTED_TYPE = 4;

    private List<Recipe> mRecipes;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i){
            case RECIPE_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_exhausted_item, viewGroup, false);
                return new SearchExhaustedViewHolder(view);
            }
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, viewGroup, false);
                return new CategoryViewHolder(view, onRecipeListener);
            }
            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if(itemViewType == RECIPE_TYPE){
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipes.get(i).getImageUrl())
                    .into(((RecipeViewHolder)viewHolder).image);
            ((RecipeViewHolder) viewHolder).title.setText(mRecipes.get(i).getTitle());
            ((RecipeViewHolder) viewHolder).publisher.setText(mRecipes.get(i).getPublisher());
            ((RecipeViewHolder) viewHolder).socialScore.setText(String.valueOf(Math.round(mRecipes.get(i).getSocialRank())));
        } else if (itemViewType == CATEGORY_TYPE){
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://pl.com.tutorials.foodrecipes/drawable/" + mRecipes.get(i).getImageUrl());
            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipes.get(i).getImageUrl())
                    .into(((CategoryViewHolder)viewHolder).categoryImage);
            ((CategoryViewHolder) viewHolder).categoryTitle.setText(mRecipes.get(i).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mRecipes.get(position).getSocialRank() == -1){
            return CATEGORY_TYPE;
        } else if(position == mRecipes.size() - 1 //position == mRecipses.size()-1 means that we are at the end of the list
                && position != 0  // postion != 0 cause we dont want to show it when its empty
                && !mRecipes.get(position).getTitle().equals("EXHAUSTED...")){ //for method that checks if there are no any results to retrieve
            return LOADING_TYPE;
        } else if(mRecipes.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        } else if(mRecipes.get(position).getTitle().equals("EXHAUSTED...")){
            return EXHAUSTED_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void setQueryExhausted(){
        hideLoading();
        Recipe exhaustedRecipe = new Recipe();
        exhaustedRecipe.setTitle("EXHAUSTED...");
        mRecipes.add(exhaustedRecipe);
        notifyDataSetChanged();
    }

    private void hideLoading(){
        if(isLoading()){
            for(Recipe recipe : mRecipes){
                if(recipe.getTitle().equals("LOADING...")){
                    mRecipes.remove(recipe);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipes != null) {
            return mRecipes.size();
        }
        return 0;
    }

    public void displayLoading(){
        if(!isLoading()){
            //set it to loading
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();
        }
    }

    public void displaySearchCategories(){
        List<Recipe> categories = new ArrayList<>();
        for(int i = 0; i < AppValues.DEFAULT_SEARCH_CATEGORIES.length;i++){
            Recipe recipe = new Recipe();
            recipe.setTitle(AppValues.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImageUrl(AppValues.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocialRank(-1);
            categories.add(recipe);
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    private boolean isLoading(){
        if(mRecipes != null && mRecipes.size() > 0){ //checking if the last item in list is LOADING... mode/title
            if(mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...")){
                return true;
            }
        }
        return false;
    }

    public void setmRecipes(List<Recipe> recipes){
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe(int position){
        if(mRecipes != null && mRecipes.size() > 0){
            return mRecipes.get(position);
        }
        return null;
    }
}
