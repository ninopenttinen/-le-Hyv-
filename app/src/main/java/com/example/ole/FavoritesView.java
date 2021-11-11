package com.example.ole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;

import java.util.List;

public class FavoritesView extends AppCompatActivity {

    AppDatabase appDatabase;
    RecipeDao recipeDao;
    List<RoomRecipeWithIngredients> recipeWithIngredients;
    private Recipe recipeToRecipeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        appDatabase = AppDatabase.getInstance(this);
        recipeDao  = appDatabase.getRecipeDao();
        recipeWithIngredients = recipeDao.getAllRecipeWithIngredients();

        String J=",";


    }


}