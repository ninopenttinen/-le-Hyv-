package com.example.ole.roomsitems;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeWithIngredients {
  @Embedded public Recipe recipe;
      @Relation(
          parentColumn = "recipeId",
          entityColumn = "fk_recipe"
      )
  public List<Ingredient> ingredients;
}
