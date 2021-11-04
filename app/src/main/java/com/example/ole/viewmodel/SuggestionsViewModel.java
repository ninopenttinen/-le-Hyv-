package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ole.model.Recipe;
import com.example.ole.repository.RecipesRepository;

import java.util.List;

public class SuggestionsViewModel extends AndroidViewModel {

    private final RecipesRepository recipesRepository;
    private LiveData<List<Recipe>> recipes;

    public SuggestionsViewModel(@NonNull Application application) {
        super(application);
        recipesRepository = new RecipesRepository();
        recipes = recipesRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
