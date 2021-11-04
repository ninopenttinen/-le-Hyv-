package com.example.ole.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.dao.SearchCriteriaDao;
import com.example.ole.dao.ShoppingListDao;
import com.example.ole.roomsitems.Ingredient;
import com.example.ole.roomsitems.Recipe;
import com.example.ole.roomsitems.SearchCriteria;
import com.example.ole.roomsitems.ShoppingList;

@Database(entities = {
    Ingredient.class,
    Recipe.class,
    SearchCriteria.class,
    ShoppingList.class,
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static String DB_NAME = "Ole-DB";
    private static volatile AppDatabase instance;

    protected AppDatabase() {};

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
            .allowMainThreadQueries()
            .build();
    }

    public abstract IngredientDao getIngredientDao();
    public abstract RecipeDao getRecipeDao();
    public abstract ShoppingListDao getShoppingListDao();
    public abstract SearchCriteriaDao getSearchCriteriaDao();
}
