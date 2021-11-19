package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ole.repository.SavedDataRepository;

import java.util.List;


public class ShoppingListViewModel extends AndroidViewModel {

  private final SavedDataRepository savedDataRepository;

  public ShoppingListViewModel(@NonNull Application application) {
    super(application);

    savedDataRepository = new SavedDataRepository(application);
  }

  public void removeIngredientFromShoppingList(String ing) {
    savedDataRepository.removeIngredientFromShoppingList(ing);
  }

}
