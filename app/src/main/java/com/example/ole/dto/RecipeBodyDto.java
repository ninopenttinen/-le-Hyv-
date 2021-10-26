package com.example.ole.dto;

import com.google.gson.annotations.SerializedName;

public class RecipeBodyDto {
    public RecipeDto getRecipeDto() {
        return recipeDto;
    }

    @SerializedName("recipe")
    private RecipeDto recipeDto;
}
