package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Recipe {

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

  @ColumnInfo(name = "favourite")
  private boolean favourite;

}
