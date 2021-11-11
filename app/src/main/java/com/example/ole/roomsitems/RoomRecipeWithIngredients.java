package com.example.ole.roomsitems;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RoomRecipeWithIngredients {
  @Embedded public RoomRecipe roomRecipe;
      @Relation(
          parentColumn = "recipeId",
          entityColumn = "fk_recipe"
      )
  public List<RoomIngredient> roomIngredients;
}
