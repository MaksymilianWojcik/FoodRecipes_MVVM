package pl.com.tutorials.foodrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Recipe implements Parcelable {

    private String title;
    private String publisher;
    private String[] ingredients;
    @SerializedName("recipe_id")
    private String recipeId;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("social_rank")
    private float socialRank;

    public Recipe() {}

    public Recipe(String title, String publisher, String[] ingredients, String recipeId, String imageUrl, float socialRank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.recipeId = recipeId;
        this.imageUrl = imageUrl;
        this.socialRank = socialRank;
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        recipeId = in.readString();
        imageUrl = in.readString();
        socialRank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(float socialRank) {
        this.socialRank = socialRank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipeId='" + recipeId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", socialRank=" + socialRank +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeStringArray(ingredients);
        dest.writeString(recipeId);
        dest.writeString(imageUrl);
        dest.writeFloat(socialRank);
    }
}
