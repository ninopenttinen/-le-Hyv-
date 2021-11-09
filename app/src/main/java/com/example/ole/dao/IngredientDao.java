package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ole.roomsitems.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredients")
    List<Ingredient> getAll();

    @Query("SELECT * FROM ingredients WHERE ingredient_name LIKE :name LIMIT 1")
    Ingredient findByIngredientName(String name);

    @Insert
    long[] insertAll(Ingredient... ingredient);

    @Delete
    void delete(Ingredient ingredient);

}
