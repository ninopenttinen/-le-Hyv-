package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {

  @Query("SELECT ingredient FROM shopping_list")
  List<String> getAllIngredients();

}
