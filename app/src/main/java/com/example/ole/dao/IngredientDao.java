package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.ole.roomsitems.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAll();

}
