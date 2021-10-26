package com.example.ole.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ole.api.EdamamApi;
import com.example.ole.dto.RecipeBodyDto;
import com.example.ole.dto.RecipesJsonResponse;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesRepository {
    private static final String URL = "https://api.edamam.com";
    private final EdamamApi edamamApi;
    private MutableLiveData<List<Recipe>> recipes;

    public RecipesRepository() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        edamamApi = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EdamamApi.class);

        fetchRecipes();
    }

    private void fetchRecipes() {
        edamamApi.fetchRecipes().enqueue(new Callback<RecipesJsonResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipesJsonResponse> call, @NonNull Response<RecipesJsonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Recipe> newRecipes =  new ArrayList<>();

                    for (RecipeBodyDto recipeDto : response.body().getRecipeBody()) {
                        Recipe newRecipe = new Recipe(
                            recipeDto.getRecipeDto().getLabel(),
                            recipeDto.getRecipeDto().getImage(),
                            recipeDto.getRecipeDto().getUrl(),

                            recipeDto.getRecipeDto().getIngredientsDto().stream()
                                    .map(ingredientDto -> new Ingredient(
                                            ingredientDto.getText(),
                                            ingredientDto.getMeasure(),
                                            ingredientDto.getQuantity(),
                                            ingredientDto.getFood()
                                    )).collect(Collectors.toList()),
                                recipeDto.getRecipeDto().getCalories(),
                                recipeDto.getRecipeDto().getTotalTime()
                        );
                        newRecipes.add(newRecipe);
                    }
                    recipes.postValue(newRecipes);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipesJsonResponse> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace());
            }
        });
    }
}
