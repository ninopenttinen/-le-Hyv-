package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "recipes")
@Data
@NoArgsConstructor
public class RoomRecipe {

  @ColumnInfo(name = "recipeId")
  @PrimaryKey(autoGenerate = true)
  private long recipeId;

  @ColumnInfo(name = "recipe_name")
  private String name;

  @ColumnInfo(name = "image_url")
  private String imageUrl;

  @ColumnInfo(name = "image_data", typeAffinity = ColumnInfo.BLOB)
  private byte[] imageData;

  @ColumnInfo(name = "recipe_url")
  private String recipeUrl;

  @ColumnInfo(name = "preparation_time")
  private String preparationTime;

  @ColumnInfo(name = "calories")
  private String calories;

  @ColumnInfo(name = "favourite")
  private boolean favourite;
}
