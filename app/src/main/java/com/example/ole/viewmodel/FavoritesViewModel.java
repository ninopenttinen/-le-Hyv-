package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.ole.model.Filter;
import com.example.ole.model.Recipe;
import com.example.ole.repository.SavedDataRepository;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

  private final SavedDataRepository savedDataRepository;

  private final MediatorLiveData<List<Recipe>> recipes = new MediatorLiveData<>();

  public FavoritesViewModel(@NonNull Application application) {
    super(application);
    savedDataRepository = new SavedDataRepository(application);
    recipes.addSource(savedDataRepository.getRecipes(), recipes::setValue);
  }

  public LiveData<List<Recipe>> getRecipes() {
    return recipes;
  }

  public void removeRecipeFromFavourites(Recipe recipe) {
    savedDataRepository.removeRecipeFromFavourites(recipe);
  }
}
