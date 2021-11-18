package com.example.ole.roomsitems;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "shopping_list")
@Data
@NoArgsConstructor
public class RoomShoppingListItem {

  @PrimaryKey(autoGenerate = true)
  private long shoppingListId;

  @ColumnInfo(name = "ingredient")
  private String ingredient;

  @ColumnInfo(name = "ingredient_amount")
  private float amount;
}
