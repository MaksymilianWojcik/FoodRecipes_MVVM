package pl.com.tutorials.foodrecipes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.com.tutorials.foodrecipes.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    OnRecipeListener onRecipeListener;
    CircleImageView categoryImage;
    TextView categoryTitle;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onCategoryClick(categoryTitle.getText().toString());
    }
}
