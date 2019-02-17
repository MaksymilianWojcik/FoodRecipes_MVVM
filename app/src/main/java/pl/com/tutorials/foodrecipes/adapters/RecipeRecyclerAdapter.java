package pl.com.tutorials.foodrecipes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import pl.com.tutorials.foodrecipes.R;
import pl.com.tutorials.foodrecipes.models.Recipe;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> mRecipes;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeViewHolder(view, onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(viewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mRecipes.get(i).getImageUrl())
                .into(((RecipeViewHolder)viewHolder).image);
        ((RecipeViewHolder) viewHolder).title.setText(mRecipes.get(i).getTitle());
        ((RecipeViewHolder) viewHolder).publisher.setText(mRecipes.get(i).getPublisher());
        ((RecipeViewHolder) viewHolder).socialScore.setText(String.valueOf(Math.round(mRecipes.get(i).getSocialRank())));
    }

    @Override
    public int getItemCount() {
        if(mRecipes != null) {
            return mRecipes.size();
        }
        return 0;
    }

    public void setmRecipes(List<Recipe> recipes){
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }
}
