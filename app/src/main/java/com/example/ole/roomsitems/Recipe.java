package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

  public Recipe() {
  }

  @Ignore
  public Recipe(
      String name,
      String imageUrl,
      String recipeUrl,
      String preparationTime
  ) {
    this.name = name;
    this.imageUrl = imageUrl;
    this.recipeUrl = recipeUrl;
    this.preparationTime = preparationTime;
  }

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "recipe_name")
  private String name;

  @ColumnInfo(name = "image_url")
  private String imageUrl;

  @ColumnInfo(name = "recipe_url")
  private String recipeUrl;

  @ColumnInfo(name = "preparation_time")
  private String preparationTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getRecipeUrl() {
    return recipeUrl;
  }

  public void setRecipeUrl(String recipeUrl) {
    this.recipeUrl = recipeUrl;
  }

  public String getPreparationTime() {
    return preparationTime;
  }

  public void setPreparationTime(String preparationTime) {
    this.preparationTime = preparationTime;
  }
}
