package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.ole.roomsitems.Recipe;
import com.example.ole.roomsitems.RecipeWithIngredients;

import java.util.List;

@Dao
public interface RecipeDao {

  @Query("SELECT * FROM recipes")
  List<Recipe> getAll();

  @Query("SELECT * FROM recipes WHERE recipe_name LIKE :name LIMIT 1")
  Recipe findByRecipeName(String name);

  @Transaction
  @Query("SELECT * FROM recipes")
  List<RecipeWithIngredients> getAllRecipeWithIngredients();

  @Transaction
  @Query("SELECT * FROM recipes WHERE recipe_name LIKE :name LIMIT 1")
  RecipeWithIngredients getRecipeWithIngredientsByName(String name);

  /* Returns created ID */
  @Insert
  long insertOne(Recipe recipe);

  @Insert
  long[] insertAll(Recipe... recipe);

  @Delete
  void delete(Recipe recipe);

}
