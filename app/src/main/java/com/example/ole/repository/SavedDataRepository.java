package com.example.ole.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.example.ole.common.Utility;
import com.example.ole.dao.IngredientDao;
import com.example.ole.dao.RecipeDao;
import com.example.ole.dao.ShoppingListDao;
import com.example.ole.database.AppDatabase;
import com.example.ole.model.Ingredient;
import com.example.ole.model.Recipe;
import com.example.ole.model.ShoppingListItem;
import com.example.ole.roomsitems.RoomIngredient;
import com.example.ole.roomsitems.RoomRecipe;
import com.example.ole.roomsitems.RoomRecipeWithIngredients;
import com.example.ole.roomsitems.RoomShoppingListItem;

import java.util.ArrayList;
import androidx.lifecycle.LiveData;

import com.example.ole.dao.FiltersDao;
import com.example.ole.model.Filter;
import com.example.ole.roomsitems.RoomFilter;

import java.util.List;
import java.util.stream.Collectors;


public class SavedDataRepository {

  private final AppDatabase appDatabase; // tarjoilee daot
  private final RecipeDao recipeDao; // rajapinta roomsiin
  private final IngredientDao ingredientDao;
  private final ShoppingListDao shoppingListDao;
  private FiltersDao filtersDao;

  private final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private MutableLiveData<List<Filter>> filters = new MutableLiveData<>();

  public SavedDataRepository(@NonNull Application application) {
    appDatabase = AppDatabase.getInstance(application);
    recipeDao = appDatabase.getRecipeDao();
    shoppingListDao = appDatabase.getShoppingListDao();
    ingredientDao = appDatabase.getIngredientDao();
    filtersDao = appDatabase.getFiltersDao();

    setRecipes();
    setFilters();
  }

  @Transaction
  public void removeIngredientFromShoppingList(String ing) {
    RoomShoppingListItem listItem = shoppingListDao.findByIngredientName(ing);

    if (listItem != null) {
      shoppingListDao.delete(listItem);
    }
  }

  private void setRecipes() {
    List<RoomRecipeWithIngredients> roomRecipes = recipeDao.getAllRecipeWithIngredients();
    List<Recipe> convertedRecipes = roomRecipes.stream()
            .map(r -> new Recipe(
                    r.roomRecipe.getName(),
                    Utility.byteArrayToBitmap( r.roomRecipe.getImageData() ),
                    r.roomRecipe.getRecipeUrl(),
                    r.roomIngredients.stream()
                            .map(i -> new Ingredient(i.getText(), i.getMeasure(), i.getQuantity(), i.getName()))
                            .collect(Collectors.toList()),
                    r.roomRecipe.getCalories(),
                    r.roomRecipe.getPreparationTime()
            ))
            .collect(Collectors.toList());
    recipes.setValue(convertedRecipes);
  }

  public LiveData<List<Recipe>> getRecipes() {
    return recipes;
  }

  @Transaction
  public void addRecipeToFavourites(Recipe recipe) {
    RoomRecipe roomsRecipe = new RoomRecipe();
    roomsRecipe.setImageUrl(recipe.getUrl());
    roomsRecipe.setName(recipe.getLabel());
    roomsRecipe.setPreparationTime(recipe.getTotalTime());
    roomsRecipe.setRecipeUrl(recipe.getUrl());
    roomsRecipe.setImageData( Utility.bitmapToByteArray(recipe.getImage() ));
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


  public void removeRecipeFromFavourites(Recipe recipe) {
    RoomRecipe roomRecipe = recipeDao.findUrl(recipe.getUrl());

    if (isRecipeInFavorites(recipe)) {
      roomRecipe.setFavourite(false);
      recipeDao.delete(roomRecipe);
    }
  }

  public boolean isRecipeInFavorites(Recipe recipe) {
    RoomRecipe roomRecipe = recipeDao.findUrl(recipe.getUrl());

    if (roomRecipe == null) {
      return false;
    }
    return roomRecipe.getRecipeUrl().equals(recipe.getUrl());
  }

  public void addIngredientsToCart(List<Ingredient> ingredients) {
    List<RoomShoppingListItem> listItems = new ArrayList<>();

    for (Ingredient i : ingredients) {
      RoomShoppingListItem roomShoppingListItem = new RoomShoppingListItem();
      roomShoppingListItem.setIngredient(i.getName());
      roomShoppingListItem.setAmount(i.getQuantity());
      listItems.add(roomShoppingListItem);
    }

    for (RoomShoppingListItem i : listItems) {
      shoppingListDao.insertOne(i);
    }
  }

  public List<ShoppingListItem> getAllShoppingListItems() {
    return shoppingListDao.getAllShoppingListItems()
        .stream()
        .map(item -> new ShoppingListItem(
            item.getIngredient(), item.getAmount()
        ))
        .collect(Collectors.toList());
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
