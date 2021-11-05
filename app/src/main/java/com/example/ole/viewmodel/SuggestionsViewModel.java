package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ole.model.Recipe;
import com.example.ole.model.SearchFilters;
import com.example.ole.repository.RecipesRepository;

import java.util.List;

public class SuggestionsViewModel extends AndroidViewModel {

    private final RecipesRepository recipesRepository;
    private LiveData<List<Recipe>> recipes;
    //private LiveData<List<searchFilters>> searchFilters; // TODO: observe and trigger recipesRepository.fetchData(); when filters change
    private SearchFilters searchFilters_PLACEHOLDER = new SearchFilters(null, null, null, null, null, null);

    public SuggestionsViewModel(@NonNull Application application, String category) {
        super(application);
        searchFilters_PLACEHOLDER.setCuisineType(category);
        recipesRepository = new RecipesRepository(application, searchFilters_PLACEHOLDER);
        recipes = recipesRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
