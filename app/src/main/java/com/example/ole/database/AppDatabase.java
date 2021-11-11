package com.example.ole.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.dao.SearchCriteriaDao;
import com.example.ole.dao.ShoppingListDao;
import com.example.ole.roomsitems.RoomIngredient;
import com.example.ole.roomsitems.RoomRecipe;
import com.example.ole.roomsitems.RoomSearchCriteria;
import com.example.ole.roomsitems.RoomShoppingList;

@Database(entities = {
    RoomIngredient.class,
    RoomRecipe.class,
    RoomSearchCriteria.class,
    RoomShoppingList.class,
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
  private static final String DB_NAME = "database";
  private static volatile AppDatabase instance;

  public static synchronized AppDatabase getInstance(Context context) {
    if (instance == null) {
      instance = create(context);
    }
    return instance;
  }

  private static AppDatabase create(Context context) {
    return Room.databaseBuilder(
        context,
        AppDatabase.class, DB_NAME)
        .allowMainThreadQueries() // Allow execute in UI threads
        .fallbackToDestructiveMigration() // Lose existing data when create new DB
        .build();
  }

  public abstract IngredientDao getIngredientDao();

  public abstract RecipeDao getRecipeDao();

  public abstract SearchCriteriaDao getSearchCriteriaDao();

  public abstract ShoppingListDao getShoppingListDao();
}
