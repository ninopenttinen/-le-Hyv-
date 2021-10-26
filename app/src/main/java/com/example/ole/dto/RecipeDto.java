package com.example.ole.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDto {
    @SerializedName("label")
    String label;
    @SerializedName("image")
    String image;

    @SerializedName("url")
    String url;

    @SerializedName("ingredients")
    List<IngredientDto> ingredientsDto;

    @SerializedName("calories")
    String calories;

    @SerializedName("totalTime")
    String totalTime;

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public List<IngredientDto> getIngredientsDto() {
        return ingredientsDto;
    }

    public String getCalories() {
        return calories;
    }

    public String getTotalTime() {
        return totalTime;
    }
}
