package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ole.roomsitems.Ingredient;
import com.example.ole.roomsitems.Recipe;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAll();

    @Query("SELECT * FROM ingredient WHERE ingredient_name LIKE :name LIMIT 1")
    Ingredient findByIngredientName(String name);

    @Insert
    void insertAll(Ingredient... ingredient);

    @Delete
    void delete(Ingredient ingredient);

}
