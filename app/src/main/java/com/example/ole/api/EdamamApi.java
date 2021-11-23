package com.example.ole.api;

import com.example.ole.dto.RecipesJsonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApi {
    @GET("/api/recipes/v2")
    Call<RecipesJsonResponse> fetchRecipes(
            @Query("diet") List<String> diet,
            @Query("health") List<String> health,
            @Query("cuisineType") String cuisineType,
            @Query("mealType") List<String> mealType,
            @Query("dishType") List<String> dishType,
            @Query("excluded") List<String> excluded
    );
}
