package com.example.ole.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.example.ole.dao.FiltersDao;
import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Filter;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.roomsitems.RoomFilter;
import com.example.ole.roomsitems.RoomIngredient;
import com.example.ole.roomsitems.RoomRecipe;

import java.util.List;
import java.util.stream.Collectors;


public class SavedDataRepository {

    private AppDatabase appDatabase; // tarjoilee daot
    private RecipeDao recipeDao; // rajapinta roomsiin
    private IngredientDao ingredientDao;
    private FiltersDao filtersDao;

    private MutableLiveData<List<Filter>> filters = new MutableLiveData<>();

    public SavedDataRepository(@NonNull Application application) {
        appDatabase = AppDatabase.getInstance(application);
        recipeDao = appDatabase.getRecipeDao();
        ingredientDao = appDatabase.getIngredientDao();
        filtersDao = appDatabase.getFiltersDao();

        setFilters();
    }

    @Transaction
    public void addRecipeToFavourites(Recipe recipe) {
        RoomRecipe roomRecipe = new RoomRecipe();
        roomRecipe.setImageUrl(recipe.getUrl());
        roomRecipe.setName(recipe.getLabel());
        roomRecipe.setPreparationTime(recipe.getTotalTime());
        roomRecipe.setRecipeUrl(recipe.getUrl());
        roomRecipe.setFavourite(true);

        long recipeID = recipeDao.insertOne(roomRecipe);

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

    private void setFilters() {
        List<RoomFilter> roomFilters = filtersDao.getAll();
        List<Filter> convertedFilters = roomFilters.stream()
                .map(f -> new Filter(f.getType(), f.getName()))
                .collect(Collectors.toList());
        filters.setValue(convertedFilters);
    }

    public void addFilter(Filter filter) {
        RoomFilter roomFilter = new RoomFilter();
        roomFilter.setName(filter.getFilterName());
        roomFilter.setType(filter.getFilterType());
        filtersDao.insert(roomFilter);
        setFilters();
    }

    public void removeFilter(Filter filter) {
        filtersDao.delete(filter.getFilterName(), filter.getFilterType());
        setFilters();
    }

    public LiveData<List<Filter>> getFilters() {
        return filters;
    }
}
