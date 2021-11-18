package com.example.ole.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomIngredient;
import com.example.ole.roomsitems.RoomRecipe;

import java.util.List;


public class SavedDataRepository {

    private AppDatabase appDatabase; // tarjoilee daot
    private RecipeDao recipeDao; // rajapinta roomsiin
    private IngredientDao ingredientDao;

    private MutableLiveData<Recipe> recipe = new MutableLiveData<>();

    public SavedDataRepository(@NonNull Application application) {

        appDatabase = AppDatabase.getInstance(application);
        recipeDao  = appDatabase.getRecipeDao();
        ingredientDao = appDatabase.getIngredientDao();

    }

    @Transaction
    public void addRecipeToFavourites(Recipe recipe) {
        RoomRecipe roomsRecipe = new RoomRecipe();
        roomsRecipe.setImageUrl(recipe.getUrl());
        roomsRecipe.setName(recipe.getLabel());
        roomsRecipe.setPreparationTime(recipe.getTotalTime());
        roomsRecipe.setRecipeUrl(recipe.getUrl());
        roomsRecipe.setFavourite(true);

        long recipeID = recipeDao.insertOne(roomsRecipe);

        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            RoomIngredient roomIngredient = new RoomIngredient();
            roomIngredient.setName(ingredient.getName());
            roomIngredient.setQuantity(ingredient.getQuantity());
            roomIngredient.setMeasure(ingredient.getMeasure());
            roomIngredient.setText(ingredient.getText());
            roomIngredient.setFk_recipe(recipeID);

            ingredientDao.insertAll(roomIngredient);
        }
    }

    public boolean isRecipeInFavorites(Recipe recipe){
        RoomRecipe roomRecipe = recipeDao.findUrl(recipe.getUrl());

        if(roomRecipe == null){
            return false;
        }
        return roomRecipe.getRecipeUrl().equals(recipe.getUrl());
    }
}
