package com.example.ole.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ole.model.ShoppingListItem;
import com.example.ole.repository.SavedDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ShoppingListViewModel extends AndroidViewModel {

  private final SavedDataRepository savedDataRepository;

  public ShoppingListViewModel(@NonNull Application application) {
    super(application);

    savedDataRepository = new SavedDataRepository(application);
  }

  public void removeIngredientFromShoppingList(String ing) {
    savedDataRepository.removeIngredientFromShoppingList(ing);
  }

  public List<String> getAllIngredients() {
    return savedDataRepository.getAllShoppingListItems()
        .stream()
        .map(ShoppingListItem::getName)
        .collect(Collectors.toList());
  }

}
