package com.example.ole.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ole.BuildConfig;
import com.example.ole.api.EdamamApi;
import com.example.ole.dto.RecipeBodyDto;
import com.example.ole.dto.RecipesJsonResponse;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.R;
import com.example.ole.model.SearchFilters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesRepository {
    private static final String URL = "https://api.edamam.com";
    private final EdamamApi edamamApi;
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>(Collections.emptyList());

    public RecipesRepository(@NonNull Application application, SearchFilters searchFilters) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String app_id = BuildConfig.API_ID;
                    String app_key = BuildConfig.API_KEY;
                    HttpUrl url = chain
                            .request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("type", "public")
                            .addQueryParameter("app_id", app_id)
                            .addQueryParameter("app_key", app_key)
                            .addQueryParameter("random", "true")
                            .build();
                    return chain.proceed(chain.request().newBuilder().url(url).build());
                })
                .build();

        edamamApi = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EdamamApi.class);

        fetchRecipes(searchFilters);
    }

    private void fetchRecipes(SearchFilters searchFilters) {
        edamamApi.fetchRecipes(
                searchFilters.getDiet(),
                searchFilters.getHealth(),
                searchFilters.getCuisineType(),
                searchFilters.getMealType(),
                searchFilters.getDishType(),
                searchFilters.getExcluded()
        ).enqueue(new Callback<RecipesJsonResponse>() {
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
                    System.out.println(newRecipes);
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

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
