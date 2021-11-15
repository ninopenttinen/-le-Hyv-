package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.ole.roomsitems.RoomRecipe;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;

import java.util.List;

@Dao
public interface RecipeDao {

  @Query("SELECT * FROM recipes")
  List<RoomRecipe> getAll();

  @Query("SELECT * FROM recipes WHERE recipe_name LIKE :name LIMIT 1")
  RoomRecipe findByRecipeName(String name);

  @Transaction
  @Query("SELECT * FROM recipes")
  List<RoomRecipeWithIngredients> getAllRecipeWithIngredients();

  @Transaction
  @Query("SELECT * FROM recipes WHERE recipe_name LIKE :name LIMIT 1")
  RoomRecipeWithIngredients getRecipeWithIngredientsByName(String name);

  /* Returns created ID */
  @Insert
  long insertOne(RoomRecipe roomRecipe);

  @Insert
  long[] insertAll(RoomRecipe... roomRecipe);

  @Delete
  void delete(RoomRecipe roomRecipe);

  @Query("DELETE FROM recipes")
  void deleteIt();
}
