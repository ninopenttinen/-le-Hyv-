package com.example.ole.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipesJsonResponse {
    @SerializedName("hits")
    private List<RecipeBodyDto> recipeBodyDto;

    public List<RecipeBodyDto> getRecipeBody() { return recipeBodyDto; }
}
