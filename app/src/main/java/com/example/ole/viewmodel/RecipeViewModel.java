package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ole.model.Recipe;
import com.example.ole.repository.SavedDataRepository;

public class RecipeViewModel extends AndroidViewModel {

    private final SavedDataRepository savedDataRepository;

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        savedDataRepository = new SavedDataRepository(application);
    }

    public void addRecipeToFavourites(Recipe recipe) {
        savedDataRepository.addRecipeToFavourites(recipe);
    }
}
