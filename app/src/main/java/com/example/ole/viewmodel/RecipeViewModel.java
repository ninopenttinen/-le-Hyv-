package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.repository.SavedDataRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

  private final SavedDataRepository savedDataRepository;

  public RecipeViewModel(@NonNull Application application) {
    super(application);

    savedDataRepository = new SavedDataRepository(application);
  }

  public void addRecipeToFavourites(Recipe recipe) {
    savedDataRepository.addRecipeToFavourites(recipe);
  }

  public void removeRecipeFromFavourites(Recipe recipe) {
    savedDataRepository.removeRecipeFromFavourites(recipe);
  }

  public boolean checkFavorites(Recipe recipe) {
    return savedDataRepository.isRecipeInFavorites(recipe);
  }

  public void addToCart(List<Ingredient> ing) {
    savedDataRepository.addIngredientsToCart(ing);
  }
}
