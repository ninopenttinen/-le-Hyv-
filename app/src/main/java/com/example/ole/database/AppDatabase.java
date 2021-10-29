package com.example.ole.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.roomsitems.Ingredient;
import com.example.ole.roomsitems.Recipe;

// TODO: Make singleton
@Database( entities = {Recipe.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
}
