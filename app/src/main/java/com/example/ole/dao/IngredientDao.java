package com.example.ole.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ole.roomsitems.RoomIngredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredients")
    List<RoomIngredient> getAll();

    @Query("SELECT * FROM ingredients WHERE ingredient_name LIKE :name LIMIT 1")
    RoomIngredient findByIngredientName(String name);

    @Insert
    long[] insertAll(RoomIngredient... roomIngredient);

    @Delete
    void delete(RoomIngredient roomIngredient);

}
