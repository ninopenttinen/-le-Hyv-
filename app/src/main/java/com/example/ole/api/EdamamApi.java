package com.example.ole.api;

import com.example.ole.dto.RecipesJsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EdamamApi {
    @GET("/api/recipes/v2?type=public&q=chicken&app_id=8187bfdb&app_key=3f4454d3ecf6ff234ff360304166448d&cuisineType=Asian&random=true")
    Call<RecipesJsonResponse> fetchRecipes();
}
