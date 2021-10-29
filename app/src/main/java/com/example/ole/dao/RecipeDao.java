package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ole.roomsitems.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

  @Query("SELECT * FROM recipe")
  List<Recipe> getAll();

  @Query("SELECT * FROM recipe WHERE recipe_name LIKE :name LIMIT 1")
  Recipe findByRecipeName(String name);

  @Insert
  void insertAll(Recipe... recipe);

  @Delete
  void delete(Recipe recipe);
}
